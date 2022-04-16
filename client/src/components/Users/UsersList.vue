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
                <img
                  src="../../assets/list-svgrepo-com.svg"
                  width="25"
                  alt="list svg"
                /> </router-link
            ></span>
            <span>
              <img
                src="../../assets/user-edit-svgrepo-com.svg"
                width="30"
                alt="edit user svg"
                @click="$emit('updateUser', user.userId)"
              />
            </span>
            <span>
              <img
                src="../../assets/user-delete-svgrepo-com.svg"
                width="30"
                alt="delete user svg"
                @click="$emit('deleteUser', user.userId)"
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
