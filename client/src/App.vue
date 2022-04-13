<template>
  <div>
    <base-card>
      <the-header></the-header>
    </base-card>

    <router-view></router-view>
  </div>
</template>

<script>
import TheHeader from "./components/TheHeader.vue";
export default {
  name: "App",

  components: {
    TheHeader,
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

.early {
  background-color: green;
}
.late {
  background-color: red;
}
.intime {
  background-color: gold;
}
</style>
