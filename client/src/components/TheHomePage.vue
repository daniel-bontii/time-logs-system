<template>
  <base-card class="login jumbotron">
    <form class="login-form">
      <div v-if="errorMessage" class="alert alert-danger" role="alert">
        {{ errorMessage }}
      </div>
      <div class="form-group">
        <label for="userEmail">Email</label>
        <input
          type="email"
          class="form-control"
          id="userEmail"
          aria-describedby="email"
          v-model="loginDetails.email"
        />
      </div>
      <div class="form-group">
        <label for="userPassword">Password</label>
        <input
          type="password"
          class="form-control"
          id="userPassword"
          aria-describedby="password"
          v-model="loginDetails.password"
        />
      </div>
      <div>
        <button type="button" class="btn btn-secondary" @click="login">
          Login
        </button>
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

<style scoped>
base-card {
  background-color: aqua;
}
.login-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 50vh;
  width: 25%;
  background-color: hsla(0, 0%, 100%, 0.7);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}

label {
  display: block;
}
</style>
