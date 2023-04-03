<template>
  <v-container fluid>
    <v-row align="center">
      <v-col class="d-flex" cols="12" sm="6">
        <v-select
          :items="factoryList"
          label="Outlined style"
          outlined
          :value="factory"
          @change="changeFactory"
        ></v-select>
      </v-col>
      <v-col class="d-flex" cols="12" sm="6">
        <v-select
          :items="deptList"
          label="Outlined style"
          outlined
          :value="dept"
          @change="changeDept"
        ></v-select>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
// import axios from 'axios';

export default {
  props: {
    factory: Number,
    dept: Number,
  },
  computed: {
    factoryList() {
      return this.$store.state.factoryList;
    },
    deptList() {
      return this.$store.state.deptList;
    },
  },
  methods: {
    changeFactory(value) {
      this.$store.commit('setFactory', value);
      this.$emit('fetchProducts');
    },
    changeDept(value) {
      this.$store.commit('setDept', value);
      this.$emit('fetchProducts');
    },
  },
};
</script>

<style></style>
