<template>
  <div>
    <slot></slot>
  </div>
</template>

<style scoped>
div {
  background-color: antiquewhite;
  margin: 1%;
}
.ins-outs {
  display: inline-flex;
  width: 30%;
  background-color: blueviolet;
  height: 30%;
  align-items: center;
  justify-content: center;
  margin-left: 15%;
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
</style>
