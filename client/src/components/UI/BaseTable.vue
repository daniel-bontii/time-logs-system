<template>
  <figure>
    <figcaption>
      <slot name="caption"></slot>
    </figcaption>
    <table class="table">
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
figure {
  height: 54vh;
  overflow-y: scroll;
}
figcaption {
  /* display: block; */
  /* display: flex; */
  /* align-items: center; */
  /* align-self: center; */
  margin: 0 auto;
  /* justify-content: center; */
  /* background-color: rgb(23, 34, 59); */
  width: 40%;
  text-align: center;
  color: rgb(23, 34, 59);
  height: 5vh;
  border-radius: 10px;
  font-size: 1.5rem;
  margin-bottom: 1%;
}
table {
  width: 100%;
  text-align: left;
}
</style>
