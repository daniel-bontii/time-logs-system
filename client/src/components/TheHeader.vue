<template>
  <header class="container-fluid">
    <div class="container">
      <p class="welcome">Welcome {{ loggedInUser.name }}</p>
      <button type="button" class="btn btn-danger">Logout</button>
    </div>
  </header>
</template>

<script>
export default {
  props: ["loggedInUser"],
};
</script>

<style scoped>
header {
  height: 10vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #17223b;
  margin-bottom: 2.5rem;
}

.container {
  align-self: center;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.welcome {
  justify-self: center;
  margin-bottom: 0;
  margin-right: 1%;
}
</style>
