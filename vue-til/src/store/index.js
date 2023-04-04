import Vue from 'vue';
import Vuex from 'vuex';
import {
  getAuthFromCookie,
  getUserFromCookie,
  saveAuthToCookie,
  saveUserToCookie,
  saveFactoriesToCookie,
  saveDeptsToCookie,
  getFactoriesFromCookie,
  getDeptsFromCookie,
  saveFactoryToCookie,
  getFactoryFromCookie,
  getDeptFromCookie,
  saveDeptToCookie,
} from '@/utils/cookies';
import { loginUser } from '@/api/auth';
import { fetchProduct, fetchDept, fetchFactory } from '@/api/product';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    username: getUserFromCookie() || '',
    token: getAuthFromCookie() || '',
    factoryList: getFactoriesFromCookie() || [],
    deptList: getDeptsFromCookie() || [],
    productList: [],
    factory: getFactoryFromCookie() || null,
    dept: getDeptFromCookie() || null,
    obj: {
      1: [1, 2, 3],
      2: [4, 5],
      3: [1, 3],
      4: [2, 5],
      5: [1],
    },
  },
  getters: {
    isLogin: state => {
      return state.username !== '';
    },
    getDeptList(state) {
      return state.deptList.filter(dept => {
        return state.obj[state.factory].includes(dept.value);
      });
    },
    getDept(state) {
      return state.dept;
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
      state.dept = state.obj[value][0];
      saveFactoryToCookie(state.factory);
      saveDeptToCookie(state.dept);
    },
    setDept(state, value) {
      state.dept = value;
      saveDeptToCookie(state.dept);
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
    async FETCH_PRODUCTS({ commit }) {
      const { data } = await fetchProduct();
      commit('setProductList', data);
      return data;
    },
    async FETCH_FACTORIES({ commit }) {
      const { data } = await fetchFactory();
      commit('setFactoryList', data);
      commit('setFactory', data[2].value);
      saveFactoriesToCookie(JSON.stringify(data));
      return data;
    },
    async FETCH_DEPTS({ commit }) {
      const { data } = await fetchDept();
      commit('setDeptList', data);
      // commit('setDept', data[3].value);
      saveDeptsToCookie(JSON.stringify(data));
      return data;
    },
  },
});
