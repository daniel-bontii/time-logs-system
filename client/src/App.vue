<template>
  <router-view></router-view>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
}

.early {
  background-color: rgba(0, 128, 0, 0.1);
}
.late {
  background-color: rgba(255, 0, 0, 0.1);
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
