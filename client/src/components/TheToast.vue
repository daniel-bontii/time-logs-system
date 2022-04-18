<template>
  <div class="alert alert-success" role="alert">
    <slot></slot>
  </div>
</template>

<style scoped>
.alert {
  width: 25%;
  margin: 0 auto;
  position: absolute;
  z-index: 5;
  left: 38%;
}
</style>
