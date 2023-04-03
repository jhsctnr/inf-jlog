import Vue from 'vue';
import Vuex from 'vuex';
import {
  getAuthFromCookie,
  getUserFromCookie,
  saveAuthToCookie,
  saveUserToCookie,
} from '@/utils/cookies';
import { loginUser } from '@/api/auth';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    username: getUserFromCookie() || '',
    token: getAuthFromCookie() || '',
    factoryList: [],
    deptList: [],
    productList: [],
    factory: null,
    dept: null,
  },
  getters: {
    isLogin: state => {
      return state.username !== '';
    },
  },
  mutations: {
    setUsername(state, username) {
      state.username = username;
    },
    clearUsername(state) {
      state.username = '';
    },
    clearToken(state) {
      state.token = '';
    },
    setToken(state, token) {
      state.token = token;
    },
    setFactoryList(state, factoryList) {
      state.factoryList = factoryList;
    },
    setDeptList(state, deptList) {
      state.deptList = deptList;
    },
    setFactory(state, value) {
      state.factory = value;
    },
    setDept(state, value) {
      state.dept = value;
    },
    setProductList(state, productList) {
      state.productList = productList;
    },
  },
  actions: {
    async LOGIN({ commit }, userData) {
      const { data } = await loginUser(userData);
      commit('setToken', data.accessToken);
      commit('setUsername', data.userName);
      saveAuthToCookie(data.accessToken);
      saveUserToCookie(data.userName);
      return data;
    },
  },
});
