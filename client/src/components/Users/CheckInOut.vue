<template>
  <section>
    <base-card class="ins-outs container">
      <button type="button" @click="$emit('checkIn')">Check in</button>

      <button type="button" @click="$emit('checkOut')">Check Out</button>
    </base-card>
  </section>
</template>

<script>
export default {
  // emits: ["checkIn", "checkOut"],
};
</script>
<style scoped></style>
