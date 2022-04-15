<template>
  <base-card>
    <base-table>
      <template #caption>Employees List</template>
      <template #table-heading>
        <th>Name</th>
        <th>Email</th>
        <th>Department</th>
        <th></th>
      </template>
      <template #table-body>
        <tr :key="user.userId" v-for="user in users">
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.department }}</td>

          <td>
            <span
              ><router-link :to="`/users/${user.userId}/logs`">
                <button type="button">Logs</button>
              </router-link></span
            >
            <span
              ><base-button @click="$emit('updateUser', user.userId)"
                >Edit</base-button
              ></span
            >
            <span
              ><base-button @click="$emit('deleteUser', user.userId)"
                >Delete</base-button
              ></span
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
  emits: ["updateUser", "showForm"],
};
</script>
