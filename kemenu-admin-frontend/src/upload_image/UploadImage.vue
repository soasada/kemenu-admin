<template>
  <label for="inputImageUrl" class="form-label p-2">
    <span v-if="!loading">Upload Image</span>
    <span v-else>
      <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
      Uploading...
    </span>
  </label>
  <input type="file" ref="file" class="form-control" id="inputImageUrl" @change="uploadImage">
  <div class="form-text">{{ imageUrl }}</div>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import {useStore} from "vuex";
import UploadImageService from "@/upload_image/UploadImageService";

export default defineComponent({
  emits: ['url'],
  name: 'UploadImage',
  setup(props, context) {
    const store = useStore();
    const loading = ref(false);
    const file = ref<HTMLInputElement>();
    const imageUrl = ref('');

    const uploadImage = async () => {
      if (file.value!.files!.length > 0) {
        const imageFile = file.value!.files![0];
        if (imageFile.type.indexOf('image') !== -1) {
          loading.value = true;
          imageUrl.value = await UploadImageService.upload(imageFile, store.getters.getAccessToken);
          context.emit('url', imageUrl.value);
          loading.value = false;
        }
      }
    }

    return {loading, file, imageUrl, uploadImage};
  }
});
</script>

<style scoped lang="scss">

</style>