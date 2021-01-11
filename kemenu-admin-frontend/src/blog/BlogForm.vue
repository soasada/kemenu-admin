<template>
  <div id="blog-form">
    <form @submit.prevent="sendForm">
      <div class="mb-3">
        <label for="inputId" class="form-label">ID</label>
        <input type="text" class="form-control" id="inputId" v-model="blogForm.id" disabled>
      </div>

      <div class="mb-3">
        <label for="inputImageUrl" class="form-label">Image URL</label>
        <input type="text" class="form-control" id="inputImageUrl" v-model="blogForm.imageUrl">
      </div>

      <div class="mb-3">
        <label for="inputCreatedAt" class="form-label">Created At</label>
        <input type="text" class="form-control" id="inputCreatedAt" v-model="blogForm.createdAt" disabled>
      </div>

      <div class="mb-3">
        <label for="inputUpdatedAt" class="form-label">Updated At</label>
        <input type="text" class="form-control" id="inputUpdatedAt" v-model="blogForm.updatedAt" disabled>
      </div>

      <button type="submit" class="btn btn-primary">Update</button>
    </form>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType} from 'vue';
import {useStore} from 'vuex';
import BlogResponse from './BlogResponse';
import BlogService from '@/blog/BlogService';

export default defineComponent({
  name: 'BlogForm',
  props: {
    blog: {
      type: Object as PropType<BlogResponse>,
      required: true
    }
  },
  setup() {
    const store = useStore();
    return {store};
  },
  data() {
    return {
      blogForm: this.blog
    };
  },
  methods: {
    sendForm() {
      const token = this.store.getters.getAccessToken;
      BlogService.update(this.blogForm, token);
    }
  }
});
</script>

<style scoped lang="scss">

</style>
