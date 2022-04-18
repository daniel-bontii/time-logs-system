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
    <div>
      <transition>
        <div
          :class="
            toastMessage.includes('successful')
              ? 'alert alert-success'
              : 'alert alert-danger'
          "
          role="alert"
          name="msgToast"
          v-if="showToast"
        >
          {{ toastMessage }}
        </div>
      </transition>
      <check-in-out
        v-if="role === 'user'"
        @check-in="checkUserIn"
        @check-out="checkUserOut"
      ></check-in-out>
    </div>

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

    <users-list
      v-if="role === 'admin' && activeComponent === 'users-list'"
      class="container users"
      :users="users"
      @update-user="updateUser"
      @delete-user="deleteUser"
      @show-form="showForm"
    ></users-list>

    <logs-list
      v-if="role === 'admin' && activeComponent === 'logs-list'"
      class="container logs"
    ></logs-list>

    <user-logs class="logs" :role="role" v-if="role === 'user'"></user-logs>
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
      toastMessage: null,
      showToast: false,
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

    displayToast(message) {
      this.toastMessage = message;
      this.showToast = true;
      setTimeout(() => location.reload(), 2000);
      this.dismissToast();
    },
    catchError(err) {
      if (err.response) {
        this.displayToast(err.response.data.message);
      }
    },

    async checkUserIn() {
      await axios
        .post(
          `http://localhost:8080/timelogs-api/v1/logs/${this.loggedInUser.userId}/checkin`,
          { date: new Date().toISOString() }
        )
        .then(() => {
          this.displayToast("Check in successful");
        })
        .catch((error) => {
          this.catchError(error);
        });
    },
    async checkUserOut() {
      await axios
        .put(
          `http://localhost:8080/timelogs-api/v1/logs/${this.loggedInUser.userId}/checkout`,
          { date: new Date().toISOString() }
        )
        .then(() => {
          this.displayToast("Check out successful");
        })
        .catch((error) => {
          this.catchError(error);
        });
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
.logs,
.users {
  border: 1px solid rgba(255, 103, 103, 0.2);
}

.msgToast-enter-from {
  opacity: 0;
}
.msgToast-enter-to {
  opacity: 1;
}
.msgToast-enter-active {
  transition: all 2s ease;
}

.alert {
  width: 25%;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
