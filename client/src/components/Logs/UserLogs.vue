<template>
  <base-card class="container">
    <router-link
      v-if="role === 'admin'"
      :to="`/users/${this.$route.params.adminId}/dashboard`"
    >
      <button type="button" role="button" class="btn btn-secondary">
        Dashboard
      </button>
    </router-link>
    <base-table>
      <template #caption>Logs List</template>
      <template #table-heading>
        <th>Date</th>
        <th>Time In</th>
        <th>Time Out</th>
        <th v-if="role === 'admin'">Indicator</th>
      </template>
      <template #table-body>
        <tr v-if="logs.length < 1">
          <td>No logs added yet</td>
        </tr>
        <tr v-else :key="log.userId" v-for="log in logs">
          <td>{{ log.date }}</td>
          <td>{{ log.timeIn }}</td>
          <td>{{ log.timeOut }}</td>
          <td
            v-if="role === 'admin'"
            :class="log.indicator.split(' ').join('')"
          >
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
  props: ["role"],
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
    console.log(this.$route.params);
    this.getUserLogs();
  },
};
</script>

<style scoped>
.container {
  background-color: rgba(255, 253, 208, 0.1);
  border: 1px solid rgba(255, 103, 103, 0.2);
}
</style>
