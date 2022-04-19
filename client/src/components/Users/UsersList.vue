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
        <tr v-if="users.length < 1">
          <td>No users added yet</td>
        </tr>
        <tr v-else :key="user.userId" v-for="user in users">
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.department }}</td>

          <td>
            <span>
              <img
                src="../../assets/list-svgrepo-com.svg"
                width="25"
                alt="list
              svg"
                @click="$emit('viewLogs', user.userId)"
                data-toggle="tooltip"
                data-placement="top"
                title="View Logs"
              />
            </span>
            <span>
              <img
                src="../../assets/user-edit-svgrepo-com.svg"
                width="30"
                alt="edit user svg"
                @click="$emit('updateUser', user.userId)"
                data-toggle="tooltip"
                data-placement="top"
                title="Update User"
              />
            </span>
            <span>
              <img
                src="../../assets/user-delete-svgrepo-com.svg"
                width="30"
                alt="delete user svg"
                @click="$emit('deleteUser', user.userId)"
                data-toggle="tooltip"
                data-placement="top"
                title="Delete User"
              />
            </span>
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

<style scoped>
span {
  margin: 2%;
}
</style>
