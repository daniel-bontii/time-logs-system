<template>
  <base-card>
    <form @submit.prevent="login">
      <p v-if="errorMessage">{{ errorMessage }}</p>
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
      user: null,
      errorMessage: null,
      loginDetails: { email: "", password: "" },
    };
  },

  methods: {
    async login() {
      const res = await axios
        .post("http://localhost:8080/timelogs-api/v1/login", this.loginDetails)
        .catch((error) => {
          this.errorMessage = error.response.data.message;
        });

      this.user = res.data;

      if (this.user) {
        this.$router.push(`/users/${this.user.userId}/dashboard`);
      }
    },
  },
};
</script>
