<template>
  <header>
    <p>Welcome {{ loggedInUser.name }}[{{ loggedInUser.role }}]</p>
    <base-button> Logout </base-button>
  </header>
</template>

<script>
export default {
  props: ["loggedInUser"],
};
</script>

<style scoped>
header {
  display: flex;
  justify-content: flex-end;
}
</style>
