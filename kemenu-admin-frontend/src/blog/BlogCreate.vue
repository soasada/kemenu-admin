<template>
  <div class="blog-create">
    <form @submit.prevent="sendForm">
      <div class="mb-3">
        <label for="inputId" class="form-label">ID</label>
        <input type="text" class="form-control" id="inputId" v-model="blogForm.id" disabled>
      </div>

      <div class="mb-3">
        <UploadImage @url="onUrl($event)"/>
      </div>

      <div class="mb-3">
        <label for="inputCreatedAt" class="form-label">Created At</label>
        <input type="text" class="form-control" id="inputCreatedAt" v-model="blogForm.createdAt" disabled>
      </div>

      <div class="mb-3">
        <label for="inputUpdatedAt" class="form-label">Updated At</label>
        <input type="text" class="form-control" id="inputUpdatedAt" v-model="blogForm.updatedAt" disabled>
      </div>

      <button type="submit" class="btn btn-primary">Create</button>
    </form>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import UploadImage from "@/upload_image/UploadImage.vue";

export default defineComponent({
  name: 'BlogCreate',
  components: {
    UploadImage
  },
  setup() {
    const onUrl = (url: string) => {
      console.log(url);
    };

    return {onUrl};
  },
  data() {
    return {
      blogForm: {}
    };
  },
  methods: {
    sendForm() {
      return 1;
    }
  }
});
</script>
