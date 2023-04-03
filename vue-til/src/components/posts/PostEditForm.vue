<template>
  <div class="contents">
    <h1 class="page-header">Edit Post</h1>
    <div class="form-wrapper">
      <form class="form" @submit.prevent="submitForm">
        <div>
          <label for="title">Title: </label>
          <input id="title" type="text" v-model="title" />
        </div>
        <div>
          <label for="contents">Contents: </label>
          <textarea id="contents" type="text" rows="5" v-model="contents" />
          <p class="validation-text">{{ `${contentsLength} / 200` }}</p>
          <p class="validation-text warning" v-if="!isContentsValid">
            Contents must be less than 200
          </p>
        </div>
        <button
          type="submit"
          class="btn"
          :class="{ disabled: !isContentsValid || !contents || !title }"
          :disabled="!isContentsValid || !contents || !title"
        >
          Edit
        </button>
      </form>
      <p class="log">
        {{ logMessage }}
      </p>
    </div>
  </div>
</template>

<script>
import { fetchPost, editPost } from '@/api/posts';

export default {
  data() {
    return {
      title: '',
      contents: '',
      logMessage: '',
    };
  },
  computed: {
    isContentsValid() {
      return this.contents.length <= 200;
    },
    contentsLength() {
      return this.contents.length;
    },
  },
  methods: {
    async submitForm() {
      try {
        const id = this.$route.params.id;
        await editPost(id, {
          title: this.title,
          contents: this.contents,
        });
        this.$router.push('/main');
      } catch (error) {
        this.logMessage = error.response.data.message;
      }
    },
  },
  async created() {
    const id = this.$route.params.id;
    const { data } = await fetchPost(id);
    this.title = data.title;
    this.contents = data.contents;
  },
};
</script>

<style scoped>
.form-wrapper .form {
  width: 100%;
}
.btn {
  color: white;
}
</style>
