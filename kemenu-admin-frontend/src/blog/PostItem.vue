<template>
  <div class="row post-item">
    <div class="col-3">
      <router-link class="nav-link" :to="'/blog/' + blogId + '/locale/' + post.locale + '/update'">
        {{ post.title }}
      </router-link>
    </div>
    <div class="col-3">
      {{ post.locale }}
    </div>
    <div class="col-3">
      <p class="font-weight-bold">Created:</p> {{ post.createdAt }}
    </div>
    <div class="col-3">
      <p class="font-weight-bold">Updated:</p> {{ post.updatedAt }}
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType} from 'vue';
import PostResponse from '@/blog/PostResponse';

export default defineComponent({
  name: 'PostItem',
  props: {
    blogId: {
      type: String,
      required: true
    },
    post: {
      type: Object as PropType<PostResponse>,
      required: true
    }
  }
});
</script>
