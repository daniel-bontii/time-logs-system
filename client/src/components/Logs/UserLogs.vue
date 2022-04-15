<template>
  <base-card class="container">
    <router-link to="/dashboard">
      <base-button>Dashboard</base-button>
    </router-link>
    <base-table>
      <template #caption>Logs List</template>
      <template #table-heading>
        <th>Date</th>
        <th>Time In</th>
        <th>Time Out</th>
        <th>Indicator</th>
      </template>
      <template #table-body>
        <tr :key="log.userId" v-for="log in logs">
          <td>{{ log.date }}</td>
          <td>{{ log.timeIn }}</td>
          <td>{{ log.timeOut }}</td>
          <td :class="log.indicator.split(' ').join('')">
            {{ log.indicator }}
          </td>
        </tr>
      </template>
    </base-table>
  </base-card>
</template>

<script>
import axios from "axios";
import BaseButton from "../UI/BaseButton.vue";
export default {
  components: { BaseButton },
  data() {
    return {
      logs: [],
    };
  },

  methods: {
    async getUserLogs() {
      const logs = await axios.get(
        `http://localhost:8080/timelogs-api/v1/logs/${this.$route.params.userId}`
      );
      this.logs = logs.data;
    },
  },

  mounted() {
    this.getUserLogs();
  },
};
</script>
