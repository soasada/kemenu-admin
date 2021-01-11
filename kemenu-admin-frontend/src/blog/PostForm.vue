<template>
  <div id="post-form">
    <form @submit.prevent="sendForm">
      <div class="mb-3">
        <label for="inputTitle" class="form-label">Title</label>
        <input type="text" class="form-control" id="inputTitle" v-model="postForm.title">
      </div>

      <div class="mb-3">
        <label for="inputContent" class="form-label">Content</label>
        <input type="text" class="form-control" id="inputContent" v-model="postForm.content">
      </div>

      <div class="mb-3">
        <label for="inputLocale" class="form-label">Locale</label>
        <select id="inputLocale" class="form-select" v-model="postForm.locale">
          <option value="en">English</option>
          <option value="es">Spanish</option>
          <option value="ca">Catalan</option>
        </select>
      </div>

      <button type="submit" class="btn btn-primary">{{ postForm.id ? 'Update' : 'Create' }}</button>
    </form>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType} from 'vue';
import {useStore} from 'vuex';
import BlogResponse from './BlogResponse';
import BlogService from '@/blog/BlogService';
import PostResponse from '@/blog/PostResponse';

export default defineComponent({
  name: 'PostForm',
  props: {
    blog: {
      type: Object as PropType<BlogResponse>,
      required: false
    },
    post: {
      type: Object as PropType<PostResponse>,
      required: false
    }
  },
  setup() {
    const store = useStore();
    return {store};
  },
  data() {
    return {
      postForm: this.post || {id: ''}
    };
  },
  methods: {
    sendForm() {
      const token = this.store.getters.getAccessToken;

      if (this.postForm && this.postForm.id) {
        BlogService.update(this.postForm, token);
      } else {
        BlogService.create(this.blogForm, token);
      }
    }
  }
});
</script>

<style scoped lang="scss">

</style>
