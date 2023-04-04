<template>
  <v-container fluid>
    <v-row align="center">
      <v-col class="d-flex" cols="12" sm="6">
        <FactorySelect />
      </v-col>
      <v-col class="d-flex" cols="12" sm="6">
        <DeptSelect />
      </v-col>
    </v-row>
    <v-row align="center">
      <v-col class="d-flex" cols="12" sm="6">
        <FactorySelect />
      </v-col>
      <v-col class="d-flex" cols="12" sm="6">
        <DeptSelect />
      </v-col>
    </v-row>
    <v-row align="center">
      <v-col class="d-flex" cols="12" sm="6">
        <FactorySelect />
      </v-col>
      <v-col class="d-flex" cols="12" sm="6">
        <DeptSelect />
      </v-col>
    </v-row>
    <v-row align="center">
      <v-col class="d-flex" cols="12" sm="6">
        <FactorySelect />
      </v-col>
      <v-col class="d-flex" cols="12" sm="6">
        <DeptSelect />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import FactorySelect from '@/components/testcomp/FactorySelect.vue';
import DeptSelect from '@/components/testcomp/DeptSelect.vue';

export default {
  components: {
    FactorySelect,
    DeptSelect,
  },
};
</script>

<style></style>
