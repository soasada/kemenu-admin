<template>
  <div class="blog-post-create">
    <BorderBottomTitle title="Update a Blog post"/>

    <form @submit.prevent="sendForm">
      <div class="mb-3">
        <label for="input-blog-title" class="form-label">Title</label>
        <input type="text" class="form-control" id="input-blog-title" v-model="blogPost.title">
      </div>

      <div class="mb-3">
        <label for="input-blog-content" class="form-label">Content</label>
        <textarea class="form-control" id="input-blog-content" rows="3" v-model="blogPost.content"></textarea>
      </div>

      <div class="mb-3">
        <label for="input-blog-locale" class="form-label">Locale</label>
        <select id="input-blog-locale" class="form-select" v-model="blogPost.locale">
          <option value="es">Spanish</option>
          <option value="en">English</option>
          <option value="ca">Catalan</option>
        </select>
      </div>

      <button type="submit" class="btn btn-primary">Update</button>
    </form>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import {useStore} from 'vuex';
import BorderBottomTitle from '@/layout/BorderBottomTitle.vue';
import {useRoute, useRouter} from 'vue-router';
import BlogService from '@/blog/BlogService';

export default defineComponent({
  name: 'BlogPostUpdate',
  components: {
    BorderBottomTitle
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    const router = useRouter();

    const blogId = route.params.blogId as string;
    const locale = route.params.locale as string;

    const findBlog = store.getters.findBlog;
    const blog = findBlog(blogId);
    const blogPost = blog.posts[locale];

    const sendForm = () => {
      const token = store.getters.getAccessToken;
      store.dispatch('clearBlogs'); // this should be inside the service
      BlogService.update(blogId, {
        title: blogPost.title,
        content: blogPost.content,
        imageUrl: blog.imageUrl,
        locale: blogPost.locale
      }, token, () => router.push('/blog'));
    };

    return {store, route, router, blogPost, sendForm};
  }
});
</script>
