<template>
  <section>
    <base-card>
      <the-header :loggedInUser="loggedInUser ? loggedInUser : ''"></the-header>
    </base-card>

    <base-card v-if="role === 'admin'" class="admin-nav container">
      <div>
        <button
          type="button"
          @click="changeComponent('users-list')"
          :class="
            activeComponent === 'users-list'
              ? 'btn btn-light'
              : 'btn btn-outline-light'
          "
        >
          Employees
        </button>
        <button
          type="button"
          @click="changeComponent('logs-list')"
          :class="
            activeComponent === 'logs-list'
              ? 'btn btn-light'
              : 'btn btn-outline-light'
          "
        >
          Logs
        </button>
      </div>
      <button
        type="button"
        class="btn btn-light new-employee"
        @click="showForm"
      >
        Add Employee
      </button>
    </base-card>

    <check-in-out
      v-if="role === 'user'"
      @check-in="checkUserIn"
      @check-out="checkUserOut"
    ></check-in-out>

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

    <base-card
      v-if="role === 'admin' && activeComponent === 'users-list'"
      class="container"
    >
      <users-list
        :users="users"
        @update-user="updateUser"
        @delete-user="deleteUser"
        @show-form="showForm"
      ></users-list>
    </base-card>

    <base-card
      v-if="role === 'admin' && activeComponent === 'logs-list'"
      class="container"
    >
      <logs-list></logs-list>
    </base-card>

    <user-logs v-if="role === 'user'"></user-logs>
  </section>
</template>

<script>
import UsersList from "./Users/UsersList.vue";
import LogsList from "./Logs/LogsList.vue";
import BaseCard from "./UI/BaseCard.vue";
import EmployeeForm from "./Users/EmployeeForm.vue";
import CheckInOut from "./Users/CheckInOut.vue";

import TheHeader from "./TheHeader.vue";
import axios from "axios";
import UserLogs from "./Logs/UserLogs.vue";

export default {
  data() {
    return {
      loggedInUser: null,
      role: null,
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
    EmployeeForm,
    CheckInOut,
    TheHeader,
    UserLogs,
  },

  methods: {
    async addorUpdate() {
      if (!this.newUser.userId) {
        await axios
          .post("http://localhost:8080/timelogs-api/v1/users", this.newUser)
          .catch((error) => {
            this.errorMessage = error.response.data.message;
          });
        location.reload();
      } else {
        await axios
          .put(
            `http://localhost:8080/timelogs-api/v1/users/${this.newUser.userId}`,
            this.newUser
          )
          .catch((err) => {
            this.errorMessage = err.response.data.message;
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
    },

    async checkUserIn() {
      await axios.post(
        `http://localhost:8080/timelogs-api/v1/logs/${this.loggedInUser.userId}/checkin`,
        { date: new Date().toISOString() }
      );
      location.reload();
    },
    async checkUserOut() {
      await axios.put(
        `http://localhost:8080/timelogs-api/v1/logs/${this.loggedInUser.userId}/checkout`,
        { date: new Date().toISOString() }
      );
      location.reload();
    },
  },

  async mounted() {
    await axios
      .get(
        `http://localhost:8080/timelogs-api/v1/login/${this.$route.params.userId}`
      )
      .then((res) => {
        this.loggedInUser = res.data;
        this.role = res.data.role;
      })
      .catch((error) => {
        if (error.response) {
          this.$router.push("/");
        }
      });

    this.getUsers();
  },
};
</script>

<style scoped>
.new-employee {
  box-shadow: 2px 2px 5px 5px rgba(177, 174, 174, 0.25);
}
</style>
