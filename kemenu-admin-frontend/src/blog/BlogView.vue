<template>
  <div class="blog">
    <BorderBottomTitle title="Blog"/>

    <button type="button" class="btn btn-primary btn-lg mb-5" @click="createBlog">Create Blog</button>

    <div v-if="store.getters.isLoadingBlogs">
      <div class="text-center spinner-border" style="width: 3rem; height: 3rem;" role="status"></div>
    </div>

    <ul class="list-group">
      <li class="list-group-item" v-for="blog in store.getters.getAllBlogs" :key="blog.id">
        <BlogItem :blog="blog"/>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import {useStore} from 'vuex';
import BorderBottomTitle from '@/layout/BorderBottomTitle.vue';
import BlogResponse from '@/blog/BlogResponse';
import BlogItem from '@/blog/BlogItem.vue';
import {useRouter} from 'vue-router';

export default defineComponent({
  name: 'BlogView',
  components: {
    BorderBottomTitle,
    BlogItem
  },
  setup() {
    const store = useStore();
    const blogs = ref<BlogResponse[]>([]);
    const router = useRouter();

    store.dispatch('findAllBlogs');

    const onEdit = (blog: BlogResponse) => {
      console.log('Editing: ' + blog.id);
    };

    const onDelete = (blog: BlogResponse) => {
      console.log('Deleting: ' + blog.id);
    };

    const createBlog = () => {
      router.push('/blog/create');
    }

    return {store, blogs, onEdit, onDelete, createBlog};
  }
});
</script>
