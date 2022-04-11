import { createRouter, createWebHistory } from "vue-router";
import TheDashboard from "./components/TheDashboard.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [{ path: "/dashboard", component: TheDashboard }],
});

export default router;
