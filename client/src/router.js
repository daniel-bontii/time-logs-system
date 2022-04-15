import { createRouter, createWebHistory } from "vue-router";
import TheDashboard from "./components/TheDashboard.vue";
import UsersList from "./components/Users/UsersList.vue";
import LogsList from "./components/Logs/LogsList.vue";
import UserLogs from "./components/Logs/UserLogs.vue";
import TheHomePage from "./components/TheHomePage.vue";

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
    {
      path: "/users/:userId/logs",
      component: UserLogs,
      props: { role: "admin" },
    },
    {
      path: "/",
      component: TheHomePage,
    },
    {
      path: "/users/:userId/dashboard",
      component: TheDashboard,
      props: true,
    },
  ],
});

export default router;
