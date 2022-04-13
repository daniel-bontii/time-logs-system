<template>
  <base-card>
    <base-table>
      <template #caption>Logs List</template>
      <template #table-heading>
        <th>Name</th>
        <th>Date</th>
        <th>Time In</th>
        <th>Time Out</th>
        <th>Indicator</th>
      </template>
      <template #table-body>
        <tr :key="log.userId" v-for="log in logs">
          <td>{{ log.name }}</td>
          <td>{{ log.date }}</td>
          <td>{{ log.timeIn }}</td>
          <td>{{ log.timeOut }}</td>
          <td :class="log.indicator.split(' ').join('')">{{ log.indicator }}</td>
        </tr>
      </template>
    </base-table>
  </base-card>
</template>

<script>
import axios from "axios";
import BaseCard from "../UI/BaseCard.vue";
import BaseTable from "../UI/BaseTable.vue";
export default {
  components: { BaseCard, BaseTable },
  data() {
    return {
      logs: [
        {
          logId: 1,
          name: "Daniel Bontii",
          date: "2022-04-10",
          timeIn: "07:20",
          timeOut: "16:45",
          indicator: "early",
        },
        {
          logId: 2,
          name: "Nicholas Brown",
          date: "2022-04-10",
          timeIn: "07:45",
          timeOut: "16:57",
          indicator: "in time",
        },
        {
          logId: 3,
          name: "Daniel Bontii",
          date: "2022-04-11",
          timeIn: "08:20",
          timeOut: "16:00",
          indicator: "in time",
        },
      ],
    };
  },
  methods: {
    async getLogs() {
      const logs = await axios.get(
        "http://localhost:8080/timelogs-api/v1/logs"
      );
      this.logs = logs.data;
    },
  },
  mounted() {
    this.getLogs();
  },
};
</script>

<style scoped>
.early {
  background-color: green;
}
.late {
  background-color: red;
}
.intime {
  background-color: gold;
}
</style>
