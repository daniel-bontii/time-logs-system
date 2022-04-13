<template>
  <section>
    <base-card>
      <the-header></the-header>
    </base-card>

    <base-card>
      <base-button @click="changeComponent('users-list')"> Users </base-button>
      <base-button @click="changeComponent('logs-list')"> Logs </base-button>
    </base-card>

    <check-in-out></check-in-out>

    <base-card v-if="isAddingEmployee">
      <employee-form
        :newUser="newUser"
        :errorMessage="errorMessage"
        @hide-form="hideForm"
        @save-user="addOrUpdate"
        @save-or-update="addorUpdate"
        @update-detail="updateDetail"
      ></employee-form>
    </base-card>

    <base-card v-if="activeComponent === 'users-list'">
      <users-list
        :users="users"
        @update-user="updateUser"
        @delete-user="deleteUser"
        @show-form="showForm"
      ></users-list>
    </base-card>

    <base-card v-if="activeComponent === 'logs-list'">
      <logs-list></logs-list>
    </base-card>
  </section>
</template>

<script>
import UsersList from "./Users/UsersList.vue";
import LogsList from "./Logs/LogsList.vue";
import BaseCard from "./UI/BaseCard.vue";
import BaseButton from "./UI/BaseButton.vue";
import EmployeeForm from "./Users/EmployeeForm.vue";
import CheckInOut from "./Users/CheckInOut.vue";

import TheHeader from "./TheHeader.vue"
import axios from "axios";

export default {
  data() {
    return {
      activeComponent: "users-list",
      users: [],
      blankUser: { userId: null, name: "", email: "", department: "" },
      isAddingEmployee: false,
      errorMessage: null,
      newUser: { userId: null, name: "", email: "", department: "" },
    };
  },
  components: {
    UsersList,
    LogsList,
    BaseCard,
    BaseButton,
    EmployeeForm,
    CheckInOut,
    TheHeader,
  },

  methods: {
    async addorUpdate() {
      if (!this.newUser.userId) {
        console.log("tried create");
        await axios
          .post("http://localhost:8080/timelogs-api/v1/users", this.newUser)
          .catch((error) => {
            this.errorMessage = error.response.data.message;
          });
        location.reload();
      } else {
        console.log("tried update");
        await axios
          .put(
            `http://localhost:8080/timelogs-api/v1/users/${this.newUser.userId}`,
            this.newUser
          )
          .catch((err) => {
            this.errorMessage = err.response.data.message;
            console.error(err.message);
          });
        this.newUser = this.blankUser;
        location.reload();
      }
    },

    updateUser(userId) {
      this.isAddingEmployee = true;
      this.newUser = this.users.find((user) => user.userId === userId);
    },

    async getUsers() {
      const users = await axios.get(
        "http://localhost:8080/timelogs-api/v1/users"
      );

      this.users = users.data;
    },
    async deleteUser(userId) {
      await axios.delete(
        `http://localhost:8080/timelogs-api/v1/users/${userId}`
      );
      location.reload();
    },

    showForm() {
      this.isAddingEmployee = true;
      this.newUser = this.blankUser;
    },

    hideForm() {
      this.newUser = this.blankUser;
      this.isAddingEmployee = false;
    },

    changeComponent(newComponent) {
      this.activeComponent = newComponent;
    },

    updateDetail(field, event) {
      this.newUser[field] = event.target.value;
      console.log(this.newUser);
    },
  },
  mounted() {
    this.getUsers();
  },
};
</script>

<style scoped></style>
