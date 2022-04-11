<template>
  <section>
    <the-header></the-header>
    <div class="nav">
      <button type="button">Users</button>
      <button type="button">Logs</button>
    </div>
    <div class="content"></div>
  </section>
</template>

<script>
import TheHeader from "./TheHeader.vue";

export default {
  components: {
    TheHeader,
  },
};
</script>

<style scoped></style>
