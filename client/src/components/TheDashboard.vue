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
      <user-list></user-list>
    </base-card>
  </section>
</template>

<script>
import TheHeader from "./TheHeader.vue";
import UserList from "./User/UsersList.vue";

export default {
  components: {
    TheHeader,
    UserList,
  },
};
</script>

<style scoped></style>
