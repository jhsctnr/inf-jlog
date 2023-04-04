<template>
  <v-select
    :items="deptList"
    label="부서"
    outlined
    dense
    :value="dept"
    @change="changeDept"
  ></v-select>
</template>

<script>
export default {
  computed: {
    deptList() {
      return this.$store.getters.getDeptList;
    },
    dept() {
      return this.$store.getters.getDept;
    },
  },
  methods: {
    async changeDept(value) {
      this.$store.commit('setDept', value);
      const products = await this.$store.dispatch('FETCH_PRODUCTS');
      console.log(products);
    },
  },
};
</script>

<style></style>
