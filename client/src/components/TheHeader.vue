<template>
  <header class="container-fluid">
    <p class="welcome">
      Welcome {{ loggedInUser.name }}[{{ loggedInUser.role }}]
    </p>
    <button type="button" class="btn btn-danger">Logout</button>
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
  height: 10vh;
  align-items: center;
  background-color: #17223b;
  color: #fff;
  align-self: center;
  margin-bottom: 2.5rem;
}
.welcome {
  justify-self: center;
  margin-bottom: 0;
}
</style>
