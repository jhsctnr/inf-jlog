<template>
  <li>
    <div class="post-title">
      {{ postItem.title }}
    </div>
    <div class="post-contents">
      {{ postItem.contents }}
    </div>
    <div class="post-time">
      <span>{{ `${postItem.createdBy}, ` }}</span>
      {{ postItem.createdAt | formatDate }}
      <i class="icon ion-md-create" @click="routeEditPage"></i>
      <i class="icon ion-md-trash" @click="deleteItem"></i>
    </div>
  </li>
</template>

<script>
import { deletePost } from '@/api/posts';

export default {
  props: {
    postItem: {
      type: Object,
      required: true,
    },
  },
  methods: {
    async deleteItem() {
      if (confirm('You want to delete?')) {
        await deletePost(String(this.postItem.id));
        this.$emit('refresh');
      }
    },
    routeEditPage() {
      this.$router.push(`/post/${this.postItem.id}`);
    },
  },
};
</script>

<style></style>
