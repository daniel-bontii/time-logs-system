<template>
  <section>
    <base-card class="ins-outs">
      <button type="button" @click="$emit('checkIn')">Check in</button>
    </base-card>
    <base-card class="ins-outs">
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
