<template>
  <base-card>
    <form @submit.prevent="login">
      <div>
        <label for="email">Email</label>
        <input
          type="text"
          name="email"
          id="email"
          v-model="loginDetails.email"
        />
      </div>
      <div>
        <label for="password">Password</label>
        <input
          type="password"
          name="password"
          id="password"
          v-model="loginDetails.password"
        />
      </div>
      <div>
        <input type="submit" value="Login" />
      </div>
    </form>
  </base-card>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      loginDetails: { email: "", password: "" },
    };
  },

  methods: {
    async login() {
      const res = await axios
        .post("http://localhost:8080/timelogs-api/v1/login", this.loginDetails)
        .catch(function (error) {
          if (error.response) {
            console.log(error.response.status.message);
          }
        });

      console.log(res.data);
    },
  },
};
</script>
