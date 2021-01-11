<template>
  <div class="blog-update">
    <BorderBottomTitle title="Updating Blog"/>

    <ul class="list-group">
      <li class="list-group-item" v-for="post in posts" :key="post.id">
        <PostItem :post="post"/>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import BorderBottomTitle from '@/layout/BorderBottomTitle.vue';
import {useRoute} from 'vue-router';
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
    const blogId = route.params.id;

    const findBlog = store.getters.findBlog
    const posts = findBlog(blogId).posts

    return {posts};
  }
});
</script>
