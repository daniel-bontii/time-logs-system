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
</style>
