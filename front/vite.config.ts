import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(), vueJsx()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }, //vue.config.js 도 가능
  server: {
    proxy: {
      "/api": {
        // target: "http://43.200.135.179:8080",
        target: "http://localhost:8080",
        changeOrigin: true,
        // secure: false,
        rewrite: (path) => path.replace(/^\/api/, '')
      },
    }
  }
})
