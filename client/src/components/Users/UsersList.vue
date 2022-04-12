<template>
  <base-card>
    <base-table>
      <template #caption>Employees List</template>
      <template #table-heading>
        <th>Name</th>
        <th>Email</th>
        <th>Department</th>
      </template>
      <template #table-body>
        <tr :key="user.userId" v-for="user in users">
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.department }}</td>
          <td><base-button>Edit</base-button></td>
          <td><base-button>Delete</base-button></td>
        </tr>
      </template>
    </base-table>
  </base-card>
</template>

<script>
import BaseCard from "../UI/BaseCard.vue";
import BaseTable from "../UI/BaseTable.vue";

import axios from "axios";
export default {
  components: { BaseCard, BaseTable },
  data() {
    return {
      users: [],
    };
  },
  methods: {
    async getUsers() {
      const users = await axios.get(
        "http://localhost:8080/timelogs-api/v1/users"
      );
      // this.users = users
      console.log(users.data);
      this.users = users.data
    },
  },
  mounted() {
    this.getUsers();
  },
};
</script>
