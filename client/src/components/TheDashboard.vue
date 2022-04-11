<template>
  <section>
    <base-card>
      <the-header></the-header>
    </base-card>
    <base-card>
      <base-button>
        <button type="button">Users</button>
      </base-button>
      <base-button>
        <button type="button">Logs</button>
      </base-button>
    </base-card>
    <base-card></base-card>
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
