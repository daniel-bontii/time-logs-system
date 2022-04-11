<template>
  <figure>
    <figcaption>Employees List</figcaption>
    <table>
      <thead>
        <th>Name</th>
        <th>Email</th>
        <th>Department</th>
      </thead>
      <tbody>
        <tr :key="user.userId" v-for="user in users">
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.department }}</td>
          <td><base-button>Edit</base-button></td>
          <td><base-button>Delete</base-button></td>
        </tr>
      </tbody>
    </table>
  </figure>
</template>

<script>
import BaseButton from "../UI/BaseButton.vue";
export default {
  components: { BaseButton },
  data() {
    return {
      users: [
        {
          userId: 1,
          name: "Daniel Bontii",
          department: "Service Center",
          email: "daniel.bontii@amalitech.com",
        },
        {
          userId: 2,
          name: "Rexford Koomsom",
          department: "Service Center",
          email: "rexford.koomson@amalitech.com",
        },
        {
          userId: 3,
          name: "Nicholas Brown",
          department: "Training Center",
          email: "nicolas.brown@amalitech.com",
        },
      ],
    };
  },
};
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
