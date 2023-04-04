<template>
  <div>
    <TestComponent1 />
    <TestComponent2 />
    <DialogComponent />
    <FormDialog />
  </div>
</template>

<script>
import TestComponent1 from '@/components/testcomp/TestComponent1.vue';
import TestComponent2 from '@/components/testcomp/TestComponent2.vue';
import DialogComponent from '@/components/testcomp/DialogComponent.vue';
import FormDialog from '@/components/testcomp/FormDialog.vue';

export default {
  computed: {
    factory() {
      return this.$store.state.factory;
    },
    dept() {
      return this.$store.getters.getDept;
    },
  },
  async mounted() {
    console.log('mountedTestPage');
    console.log(this.factory, this.dept);
    const products = await this.$store.dispatch('FETCH_PRODUCTS');
    console.log(products);
  },
  components: {
    TestComponent1,
    TestComponent2,
    DialogComponent,
    FormDialog,
  },
};
</script>

<style scoped></style>
