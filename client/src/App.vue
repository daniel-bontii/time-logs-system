<template>
  <the-dashboard></the-dashboard>
</template>

<script>
import TheDashboard from "./components/TheDashboard.vue";

export default {
  name: "App",
  components: {
    TheDashboard,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  /* margin-top: 60px; */
}
</style>
