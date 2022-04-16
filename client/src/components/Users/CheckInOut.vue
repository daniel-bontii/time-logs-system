<template>
  <section>
    <base-card class="ins-outs container">
      <button type="button" class="btn btn-success" @click="$emit('checkIn')">
        Check In
      </button>
      <div class="separator"></div>
      <button type="button" class="btn btn-warning" @click="$emit('checkOut')">
        Check Out
      </button>
    </base-card>
  </section>
</template>

<script>
export default {
  emits: ["checkIn", "checkOut"],
};
</script>
<style scoped>
.separator {
  height: 25%;
  width: 1px;
  background-color: rgb(255, 255, 255);
  margin: 5%;
}
</style>
