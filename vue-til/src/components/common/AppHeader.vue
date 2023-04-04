<template>
  <v-app-bar app color="white" flat>
    <v-container class="py-0 fill-height">
      <div class="mr-10">
        <RouterLink :to="logoLink" class="logo">
          TIL
          <span v-if="isUserLogin">by {{ $store.state.username }}</span>
        </RouterLink>
      </div>
      <!-- <v-avatar class="mr-10" color="grey darken-1" size="32"></v-avatar> -->

      <template v-if="isUserLogin">
        <a href="javascript:;" @click="logoutUser" class="logout-button">
          Logout
        </a>
      </template>
      <template v-else>
        <v-btn to="/login" text> 로그인 </v-btn>
        <v-btn to="/signup" text> 회원가입 </v-btn>
      </template>

      <v-spacer></v-spacer>

      <v-responsive max-width="260">
        <v-text-field
          dense
          flat
          hide-details
          rounded
          solo-inverted
        ></v-text-field>
      </v-responsive>
    </v-container>
  </v-app-bar>
</template>

<script>
import { deleteAllCookies } from '@/utils/cookies';

export default {
  computed: {
    isUserLogin() {
      return this.$store.getters.isLogin;
    },
    logoLink() {
      return this.$store.getters.isLogin ? '/main' : '/login';
    },
  },
  methods: {
    logoutUser() {
      this.$store.commit('clearUsername');
      this.$store.commit('clearToken');
      // deleteCookie('til_auth');
      // deleteCookie('til_user');
      // deleteCookie('til_factories');
      // deleteCookie('til_depts');
      // deleteCookie('til_factory');
      // deleteCookie('til_dept');
      deleteAllCookies();
      this.$router.push('/login');
    },
  },
};
</script>

<style scoped>
a {
  color: #dedede;
  font-size: 18px;
  text-decoration: none;
}
a.logo {
  font-size: 30px;
  font-weight: 900;
  color: #777777;
}
.logo > span {
  font-size: 14px;
  font-weight: normal;
}
</style>
