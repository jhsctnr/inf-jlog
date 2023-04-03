<template>
  <div>
    <TestComponent1
      :factory="factory"
      :dept="dept"
      @fetchProducts="fetchProducts"
    />
    <TestComponent2 />
  </div>
</template>

<script>
import TestComponent1 from '@/components/testcomp/TestComponent1.vue';
import TestComponent2 from '@/components/testcomp/TestComponent2.vue';
import axios from 'axios';

export default {
  computed: {
    factory() {
      return this.$store.state.factory;
    },
    dept() {
      return this.$store.state.dept;
    },
  },
  methods: {
    async fetchProducts() {
      console.log(this.factory, this.dept);
      const { data } = await axios.get('/api/products');
      console.log(data);
      this.$store.commit('setProductList', data);
    },
  },
  async created() {
    console.log('create');

    const response = await axios.get('/api/factories');
    console.log(response.data);
    this.$store.commit('setFactoryList', response.data);
    this.$store.commit('setFactory', response.data[2].value);

    const response2 = await axios.get('/api/depts');
    console.log(response2.data);
    this.$store.commit('setDeptList', response2.data);
    this.$store.commit('setDept', response2.data[3].value);

    this.fetchProducts();
  },
  components: {
    TestComponent1,
    TestComponent2,
  },
};
</script>

<style scoped></style>
