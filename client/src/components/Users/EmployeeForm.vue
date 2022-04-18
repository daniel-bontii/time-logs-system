<template>
  <dialog open="true" class="jumbotron">
    <div
      v-if="message"
      :class="
        message.includes('successful')
          ? 'alert alert-success'
          : 'alert alert-danger'
      "
      role="alert"
    >
      {{ message }}
    </div>
    <input type="hidden" name="userId" :value="newUser.userId" />
    <div class="form-group">
      <label for="userName">Name</label>
      <input
        type="text"
        class="form-control"
        id="userName"
        aria-describedby="name"
        :value="newUser.name"
        @keyup="$emit('updateDetail', 'name', $event)"
      />
    </div>

    <div class="form-group">
      <label for="email">Email</label>
      <input
        type="email"
        class="form-control"
        name="email"
        id="email"
        :value="newUser.email"
        @keyup="$emit('updateDetail', 'email', $event)"
      />
    </div>

    <div class="form-group">
      <label for="department">Department</label>
      <input
        type="text"
        class="form-control"
        name="department"
        id="department"
        :value="newUser.department"
        @keyup="$emit('updateDetail', 'department', $event)"
      />
    </div>
    <div class="form-group">
      <button
        type="button"
        class="btn btn-success"
        @click="$emit('saveOrUpdate')"
      >
        Save
      </button>
      <button type="button" class="btn btn-danger" @click="$emit('hideForm')">
        Close
      </button>
    </div>
  </dialog>
</template>

<script>
export default {
  props: ["newUser", "message"],
  emits: ["hideForm", "saveOrUpdate", "updateDetail"],
};
</script>

<style scoped>
dialog {
  top: 8vh;
  left: 30%;
  margin: 0;
  width: 35%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgb(255, 255, 255);
  border: none;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
</style>
