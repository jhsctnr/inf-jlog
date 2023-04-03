const { defineConfig } = require('@vue/cli-service');
module.exports = defineConfig({
  transpileDependencies: ['vuetify'],
  devServer: {
    client: {
      overlay: false,
    },
    historyApiFallback: true,
    proxy: {
      '/api': {
        target: process.env.VUE_APP_API_URL,
        pathRewrite: { '^/api': '' },
        // changeOrigin: true,
        secure: false,
      },
    },
  },
});
