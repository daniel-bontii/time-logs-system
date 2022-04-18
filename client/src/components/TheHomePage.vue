<template>
  <div>
    <base-card class="login jumbotron">
      <form class="login-form">
        <div
          v-if="message"
          :class="
            message === 'Login successful'
              ? 'alert alert-success'
              : 'alert alert-danger'
          "
          role="alert"
        >
          {{ message }}
        </div>
        <div class="form-group">
          <label for="userEmail">Email</label>
          <input
            type="email"
            class="form-control"
            id="userEmail"
            aria-describedby="email"
            v-model.trim="loginDetails.email"
          />
        </div>
        <div class="form-group">
          <label for="userPassword">Password</label>
          <input
            type="password"
            class="form-control"
            id="userPassword"
            aria-describedby="password"
            v-model.trim="loginDetails.password"
          />
        </div>
        <div>
          <button type="button" class="btn btn-secondary" @click="login">
            Login
          </button>
        </div>
      </form>
    </base-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      user: null,
      message: null,
      loginDetails: { email: "", password: "" },
      canProceed: false,
      showToast: false,
    };
  },

  methods: {
    checkInputs() {
      if (!(this.loginDetails.email && this.loginDetails.password)) {
        this.message = "Please fill both fields";
        this.canProceed = false;
      } else {
        this.canProceed = true;
      }
    },
    async login() {
      this.checkInputs();
      if (this.canProceed) {
        const res = await axios
          .post(
            "http://localhost:8080/timelogs-api/v1/login",
            this.loginDetails
          )
          .catch((error) => {
            this.message = error.response.data.message;
          });

        this.user = res.data;

        if (this.user) {
          this.message = "Login successful";
          localStorage.setItem("login", "Logged in");
          setTimeout(() => {
            this.$router.push({
              name: "dashboard",
              params: { userId: this.user.userId, isAuthenticated: true },
            });
          }, 500);
        }
      }
    },
  },
};
</script>

<style scoped>
.login-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 50vh;
  width: 25%;
  background-color: rgba(255, 255, 255, 0.8);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}

label {
  display: block;
}
</style>
