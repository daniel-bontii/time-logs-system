<template>
  <form>
    <div v-if="errorMessage">{{ errorMessage }}</div>
    <input type="hidden" name="userId" :value="newUser.userId" />
    <div>
      <label for="name">User Name</label> <br />
      <input
        type="text"
        name="name"
        id="name"
        :value="newUser.name"
        @keyup="$emit('updateDetail', 'name', $event)"
      />
    </div>

    <div>
      <label for="email">Email</label> <br />
      <input
        type="text"
        name="email"
        id="email"
        :value="newUser.email"
        @keyup="$emit('updateDetail', 'email', $event)"
      />
    </div>

    <div>
      <label for="department">Department</label> <br />
      <input
        type="text"
        name="department"
        id="department"
        :value="newUser.department"
        @keyup="$emit('updateDetail', 'department', $event)"
      />
    </div>
    <div>
      <input type="reset" value="Reset" />
      <input type="button" value="submit" @click="$emit('saveOrUpdate')" />
      <input type="button" value="Close" @click="$emit('hideForm')" />
    </div>
  </form>
</template>

<script>
export default {
  props: ["newUser", "errorMessage"],
  emits: ["hideForm", "saveOrUpdate", "updateDetail"],
};
</script>
