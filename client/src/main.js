import { createApp } from "vue";
import App from "./App.vue";
import router from "./router.js";
import BaseButton from "./components/UI/BaseButton.vue";
import BaseCard from "./components/UI/BaseCard.vue"


const app = createApp(App);
app.use(router);
app.component('base-button', BaseButton);
app.component('base-card', BaseCard);

app.mount("#app");
