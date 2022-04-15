import { createApp } from "vue";
import App from "./App.vue";
import router from "./router.js";
import BaseButton from "./components/UI/BaseButton.vue";
import BaseCard from "./components/UI/BaseCard.vue";
import BaseTable from "./components/UI/BaseTable.vue";

const app = createApp(App);

app.use(router);

app.component("base-button", BaseButton);
app.component("base-card", BaseCard);
app.component("base-table", BaseTable);

app.mount("#app");
