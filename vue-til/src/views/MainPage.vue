<template>
  <div>
    <div class="main list-container contents">
      <h1 class="page-header">Tody I Learned</h1>
      <LoadingSpinner v-if="isLoading" />
      <ul v-else>
        <PostListItem
          v-for="postItem in postItems"
          :key="postItem._id"
          :postItem="postItem"
          @refresh="fetchData"
        />
      </ul>
    </div>
    <RouterLink to="/add" class="create-button">
      <i class="ion-md-add" />
    </RouterLink>

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
    </v-container>
  </div>
</template>

<script>
import PostListItem from '@/components/posts/PostListItem.vue';
import LoadingSpinner from '@/components/common/LoadingSpinner.vue';
import { fetchPosts } from '@/api/posts';
import DeptSelect from '@/components/testcomp/DeptSelect.vue';
import FactorySelect from '@/components/testcomp/FactorySelect.vue';

export default {
  data() {
    return {
      postItems: [],
      isLoading: false,
    };
  },
  methods: {
    async fetchData() {
      try {
        this.isLoading = true;
        const { data } = await fetchPosts();
        this.isLoading = false;
        this.postItems = data;
        console.log(data);
      } catch (error) {
        console.log(error.response.data.message);
      }
    },
  },
  created() {
    this.fetchData();
  },
  components: {
    PostListItem,
    LoadingSpinner,
    DeptSelect,
    FactorySelect,
  },
};
</script>

<style></style>
