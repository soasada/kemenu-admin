<template>
  <div class="blog-update">
    <BorderBottomTitle title="Updating Blog"/>

    <button type="button" class="btn btn-primary btn-lg mb-5" @click="createBlogPost">Create Blog Post</button>

    <ul class="list-group">
      <li class="list-group-item" v-for="post in posts" :key="post.id">
        <PostItem :post="post" :blog-id="blogId"/>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import BorderBottomTitle from '@/layout/BorderBottomTitle.vue';
import {useRoute, useRouter} from 'vue-router';
import PostItem from '@/blog/PostItem.vue';
import {useStore} from 'vuex';

export default defineComponent({
  name: 'BlogUpdate',
  components: {
    BorderBottomTitle,
    PostItem
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    const router = useRouter();
    const blogId = route.params.id;

    const findBlog = store.getters.findBlog;
    const blog = findBlog(blogId);
    const posts = blog.posts;

    const createBlogPost = () => {
      router.push({path: '/blog/' + blogId + '/post', query: {imageUrl: blog.imageUrl}});
    };

    return {blogId, posts, createBlogPost};
  }
});
</script>
