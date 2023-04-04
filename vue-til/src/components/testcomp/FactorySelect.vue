<template>
  <v-select
    :items="factoryList"
    label="회사"
    outlined
    dense
    :value="factory"
    @change="changeFactory"
  ></v-select>
</template>

<script>
export default {
  computed: {
    factoryList() {
      return this.$store.state.factoryList;
    },
    factory() {
      return this.$store.state.factory;
    },
  },
  methods: {
    async changeFactory(value) {
      this.$store.commit('setFactory', value);
      const products = await this.$store.dispatch('FETCH_PRODUCTS');
      console.log(products);
    },
  },
};
</script>

<style></style>
