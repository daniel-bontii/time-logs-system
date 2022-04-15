<template>
  <div>
    <slot></slot>
  </div>
</template>

<style scoped>
div {
  background-color: rgba(250, 235, 215, 0.3);
}
.ins-outs {
  display: flex;
  width: 100%;
  background-color: rgba(23, 34, 59, 0.1);
  height: 30%;
  align-items: center;
  justify-content: center;
  height: 20vh;
  margin-bottom: 2%;
}
section {
  display: flex;
  margin: 0 auto;
  width: 60%;
}
.login {
  display: flex;
  margin: 0;
  padding: 0;
  background-image: url("../../assets/time-bg.png");
  /* background-image: url("https://cdn.pixabay.com/photo/2017/02/09/08/54/stress-2051408_960_720.jpg"); */
  background-size: cover;
  height: 100vh;
  align-items: center;
  justify-content: center;
}

.admin-nav {
  height: 10vh;
  background-color: rgb(255, 103, 104);
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
