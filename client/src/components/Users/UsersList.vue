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
          <td>
            <base-button @click="$emit('updateUser', user.userId)"
              >Edit</base-button
            >
          </td>
          <td>
            <base-button @click="$emit('deleteUser', user.userId)"
              >Delete</base-button
            >
          </td>
        </tr>
      </template>
    </base-table>
  </base-card>
</template>

<script>
import BaseCard from "../UI/BaseCard.vue";
import BaseTable from "../UI/BaseTable.vue";

export default {
  components: { BaseCard, BaseTable },
  props: ["users"],
  emits: ["updateUser"],
};
</script>
