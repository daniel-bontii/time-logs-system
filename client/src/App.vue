<template>
  <router-view></router-view>
</template>

<script>
import axios from "axios";
export default {
  name: "App",
  data() {
    return {
      allUsers: [],
      logsToday: [],
    };
  },
  async mounted() {
    let now = new Date();
    let millisTill10 =
      new Date(now.getFullYear(), now.getMonth(), now.getDate(), 23, 59, 0, 0) -
      now;
    if (millisTill10 < 0) {
      millisTill10 += 86400000;
    }
    setTimeout(function () {
      axios.get("http://localhost:8080/timelogs-api/v1/users").then((res) => {
        this.allUsers = res.data;
      });

      axios
        .get(
          `http://localhost:8080/timelogs-api/v1/logs/all/${new Date().toISOString()}`
        )
        .then((res) => {
          this.logsToday = res.data;

          for (let user of this.allUsers) {
            const loggedInToday = this.logsToday.find(
              (log) => log.userId === user.userId
            );
            if (loggedInToday === undefined) {
              axios.post(
                `http://localhost:8080/timelogs-api/v1/logs/${user.userId}/checkin`,
                { date: new Date().toISOString(), indicator: "absent" }
              );
            }
          }
        });
    }, millisTill10);
  },
};
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

#app {
  font-family: "Montserrat", sans-serif;
}

.early {
  background-color: rgba(0, 128, 0, 0.1);
}
.late {
  background-color: rgba(255, 0, 0, 0.1);
}
.absent {
  background-color: rgba(255, 0, 0, 0.3);
}
.intime {
  background-color: rgba(255, 217, 0, 0.1);
}
.deleteUserModal {
  top: 8vh;
  left: 30%;
  margin: 0;
  width: 35%;
  height: 5vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgb(255, 255, 255);
  border: none;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
</style>
