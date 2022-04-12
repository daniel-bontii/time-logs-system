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
      <form @submit.prevent="submitData">
        <div v-if="errorMessage">{{ errorMessage }}</div>
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
      <users-list></users-list>
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
      errorMessage: null,
      newUser: { name: "", email: "", department: "" },
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
    async submitData() {
      await axios
        .post("http://localhost:8080/timelogs-api/v1/users", this.newUser)
        .catch((error) => {
          this.errorMessage = error.response.data.message;
        });
      location.reload();
    },
  },
};
</script>

<style scoped></style>
