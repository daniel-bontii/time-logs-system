<template>
  <section>
    <base-card>
      <the-header></the-header>
    </base-card>
    <base-card>
      <base-button> Users </base-button>
      <base-button> Logs </base-button>
    </base-card>
    <base-card>
      <users-list></users-list>
    </base-card>
    <base-card>
      <logs-list></logs-list>
    </base-card>
  </section>
</template>

<script>
import TheHeader from "./TheHeader.vue";
import UsersList from "./Users/UsersList.vue";
import LogsList from "./Logs/LogsList.vue";

export default {
  components: {
    TheHeader,
    UsersList,
    LogsList,
  },
};
</script>

<style scoped></style>
