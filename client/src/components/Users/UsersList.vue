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
          <td>
            <base-button @click="deleteUser(user.userId)">Delete</base-button>
          </td>
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
  methods: {
    async getUsers() {
      const users = await axios.get(
        "http://localhost:8080/timelogs-api/v1/users"
      );

      this.users = users.data;
    },
    async deleteUser(userId) {
      await axios.delete(
        `http://localhost:8080/timelogs-api/v1/users/${userId}`
      );
      location.reload();
    },
  },
  mounted() {
    this.getUsers();
  },
  updated() {
    this.getUsers();
  },
};
</script>
