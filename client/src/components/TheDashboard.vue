<template>
  <section>
    <base-card>
      <the-header
        :loggedInUser="loggedInUser ? loggedInUser : ''"
        @logOut="logUserOut"
      ></the-header>
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
        :message="message"
        @hide-form="hideForm"
        @save-user="addOrUpdate"
        @save-or-update="addorUpdate"
        @update-detail="updateDetail"
      ></employee-form>
    </base-card>

    <dialog open="true" class="jumbotron deleteUserModal" v-if="isDeletingUser">
      <div v-if="deleteMessage" class="alert alert-success" role="alert">
        {{ deleteMessage }}
      </div>
      <div>Delete User?</div>
      <div class="form-group">
        <button
          type="button"
          class="btn btn-secondary"
          @click="hideDeleteDialogue"
        >
          Cancel
        </button>
        <button
          type="button"
          class="btn btn-danger"
          @click="deleteUser(idToDelte)"
        >
          Delete
        </button>
      </div>
    </dialog>
    <users-list
      v-if="role === 'admin' && activeComponent === 'users-list'"
      class="container users"
      :users="users"
      @view-logs="viewUserLogs"
      @update-user="updateUser"
      @delete-user="showDeleteDialog"
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
  props: ["isAuthenticated"],
  data() {
    return {
      loggedInUser: null,
      adminId: null,
      role: null,
      activeComponent: "users-list",
      users: [],
      blankUser: { userId: null, name: "", email: "", department: "" },
      isAddingEmployee: false,
      message: null,
      newUser: { userId: null, name: "", email: "", department: "" },
      toastMessage: null,
      showToast: false,
      canProceed: false,
      deleteMessage: null,
      isDeletingUser: false,
      idToDelte: null,
      isLoggedOut: false,
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
    checkFields(user) {
      let regex = /[a-z0-9]+@[a-z]+.[a-z]{2,3}/;

      if (!regex.test(user.email)) {
        this.message = "Please use valid email";
        this.canProceed = false;
      } else if (!user.name || !user.email || !user.department) {
        this.message = "Please fill all fields";
        this.canProceed = false;
      } else {
        this.canProceed = true;
      }
    },
    async addorUpdate() {
      this.checkFields(this.newUser);

      if (this.canProceed) {
        if (!this.newUser.userId) {
          await axios
            .post("http://localhost:8080/timelogs-api/v1/users", this.newUser)
            .then(() => {
              this.message = "Add successful";
              setTimeout(() => location.reload(), 1000);
            })
            .catch((error) => {
              if (error.response) {
                this.message = error.response.data.message;
              }
            });
        } else {
          await axios
            .put(
              `http://localhost:8080/timelogs-api/v1/users/${this.newUser.userId}`,
              this.newUser
            )
            .then(() => {
              this.message = "Update successful";
              setTimeout(() => location.reload(), 1000);
            })
            .catch((err) => {
              this.message = err.response.data.message;
            });
          this.newUser = this.blankUser;
        }
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

    showDeleteDialog(userId) {
      this.idToDelte = userId;
      this.isDeletingUser = true;
    },
    
    hideDeleteDialogue() {
      this.isDeletingUser = false;
    },

    async deleteUser(userId) {
      await axios
        .delete(`http://localhost:8080/timelogs-api/v1/users/${userId}`)
        .then(() => {
          this.deleteMessage = "Deleted";
          setTimeout(() => location.reload(), 1000);
        });
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
      setTimeout(() => location.reload(), 1000);
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
    logUserOut() {
      localStorage.removeItem("login");
      location.reload();
    },
    viewUserLogs(_userId) {
      this.$router.push({
        name: "userLogs",
        params: { userId: _userId, adminId: this.adminId },
      });
    },
  },

  async mounted() {
    if (!localStorage.getItem("login")) {
      this.$router.push("/");
    }
    await axios
      .get(
        `http://localhost:8080/timelogs-api/v1/login/${this.$route.params.userId}`
      )
      .then((res) => {
        this.loggedInUser = res.data;
        this.role = res.data.role;
        this.adminId = res.data.userId;
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
