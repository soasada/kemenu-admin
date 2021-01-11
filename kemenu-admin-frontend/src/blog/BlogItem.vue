<template>
  <div class="row blog-item">
    <div class="col-3">
      <img :src="blog.imageUrl" width="72" height="72">
    </div>
    <div class="col-3">
      <router-link class="nav-link" :to="'/blog/update/' + blog.id">
        {{ blog.id }}
      </router-link>
    </div>
    <div class="col-3">
      <p class="font-weight-bold">Created:</p> {{ blog.createdAt }}
    </div>
    <div class="col-3">
      <p class="font-weight-bold">Updated:</p> {{ blog.updatedAt }}
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType} from 'vue';
import BlogResponse from '@/blog/BlogResponse';

export default defineComponent({
  name: 'BlogItem',
  props: {
    blog: {
      type: Object as PropType<BlogResponse>,
      required: true
    }
  }
});
</script>
