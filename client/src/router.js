import { createRouter, createWebHistory } from "vue-router";
import TheDashboard from "./components/TheDashboard.vue";
import UsersList from "./components/Users/UsersList.vue";
import LogsList from "./components/Logs/LogsList.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/dashboard", component: TheDashboard },
    {
      path: "/users",
      component: UsersList,
    },
    {
      path: "/logs",
      component: LogsList,
    },
  ],
});

export default router;
