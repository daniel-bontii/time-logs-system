const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      "^/api": {
        target: "http://localhost:8080/timelogs-api/v1",
        changeOrigin: true,
      },
    },
  },
});
