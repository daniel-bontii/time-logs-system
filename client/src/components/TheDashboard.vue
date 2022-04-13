<template>
  <section>
    <base-card>
      <the-header></the-header>
    </base-card>

    <base-card>
      <base-button> Users </base-button>
      <base-button> Logs </base-button>
    </base-card>

    <base-card>
      <form @submit.prevent="addorUpdate">
        <div v-if="errorMessage">{{ errorMessage }}</div>
        <input type="hidden" name="userId" value="newUser.id" />
        <div>
          <label for="name">User Name</label> <br />
          <input type="text" name="name" id="name" v-model="newUser.name" />
        </div>

        <div>
          <label for="email">Email</label> <br />
          <input type="text" name="email" id="email" v-model="newUser.email" />
        </div>

        <div>
          <label for="department">Department</label> <br />
          <input
            type="text"
            name="department"
            id="department"
            v-model="newUser.department"
          />
        </div>
        <div>
          <input type="reset" value="Reset" />
          <input type="submit" value="Submit" />
        </div>
      </form>
    </base-card>

    <base-card>
      <users-list
        :users="users"
        @update-user="updateUser"
        @delete-user="deleteUser"
      ></users-list>
    </base-card>

    <base-card>
      <logs-list></logs-list>
    </base-card>
  </section>
</template>

<script>
import TheHeader from "./TheHeader.vue";
import UsersList from "./Users/UsersList.vue";
import LogsList from "./Logs/LogsList.vue";
import BaseCard from "./UI/BaseCard.vue";
import BaseButton from "./UI/BaseButton.vue";
import axios from "axios";

export default {
  data() {
    return {
      users: [],
      errorMessage: null,
      newUser: { userId: null, name: "", email: "", department: "" },
    };
  },
  components: {
    TheHeader,
    UsersList,
    LogsList,
    BaseCard,
    BaseButton,
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
        console.log(this.newUser);
        await axios
          .put(
            `http://localhost:8080/timelogs-api/v1/users/${this.newUser.userId}`,
            this.newUser
          )
          .catch((err) => {
            this.errorMessage = err.response.data.message;
            console.error(err.message);
          });
        this.newUser = { userId: null, name: "", email: "", department: "" };
        location.reload();
      }
    },

    updateUser(userId) {
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
  },
  mounted() {
    this.getUsers();
  },
};
</script>

<style scoped></style>
