<template>
  <div class="blog-post-create">
    <BorderBottomTitle title="Update a Blog post"/>

    <form @submit.prevent="sendForm">
      <div class="mb-3">
        <label for="input-blog-title" class="form-label">Title</label>
        <input type="text" class="form-control" id="input-blog-title" v-model="blogRequest.title">
      </div>

      <div class="mb-3">
        <label for="input-blog-content" class="form-label">Content</label>
        <textarea class="form-control" id="input-blog-content" rows="3" v-model="blogRequest.content"></textarea>
      </div>

      <div class="mb-3">
        <label for="input-blog-locale" class="form-label">Locale</label>
        <select id="input-blog-locale" class="form-select" v-model="blogRequest.locale">
          <option value="es">Spanish</option>
          <option value="en">English</option>
          <option value="ca">Catalan</option>
        </select>
      </div>

      <button type="submit" class="btn btn-primary">Create</button>
    </form>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import BlogRequest from '@/blog/BlogRequest';
import {useStore} from 'vuex';
import BlogService from '@/blog/BlogService';
import BorderBottomTitle from '@/layout/BorderBottomTitle.vue';
import {useRoute, useRouter} from 'vue-router';

export default defineComponent({
  name: 'BlogPostUpdate',
  components: {
    BorderBottomTitle
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    const router = useRouter();
    return {store, route, router};
  },
  data() {
    return {
      blogRequest: {} as BlogRequest
    };
  },
  methods: {
    sendForm() {
      const selfRoute = this.route;
      const blogId = selfRoute.params.id as string;
      this.blogRequest.imageUrl = selfRoute.query.imageUrl as string;
      const token = this.store.getters.getAccessToken;
      this.store.dispatch('clearBlogs'); // this should be inside the service
      BlogService.createPost(blogId, this.blogRequest, token, () => this.router.push('/blog'));
    }
  }
});
</script>
