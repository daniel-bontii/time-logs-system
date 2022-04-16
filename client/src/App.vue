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
  /* -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale; */
  /* text-align: center; */
  /* color: #2c3e50; */
  /* margin-top: 60px; */
  /* background-color: rgb(255, 255, 255); */
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
</style>
