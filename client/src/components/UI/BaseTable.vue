<template>
  <figure>
    <figcaption>
      <slot name="caption"></slot>
    </figcaption>
    <table>
      <thead>
        <tr>
          <slot name="table-heading"></slot>
        </tr>
      </thead>
      <tbody>
        <slot name="table-body"></slot>
      </tbody>
    </table>
  </figure>
</template>

<script>
export default {};
</script>

<style scoped>
caption {
  display: block;
}
table {
  width: 60%;
  text-align: left;
}

</style>
