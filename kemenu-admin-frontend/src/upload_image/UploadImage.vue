<template>
  <label for="input-image-url" class="form-label p-2">
    <span v-if="!loading">Upload Image</span>
    <span v-else>
      <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
      Uploading...
    </span>
  </label>
  <input type="file" ref="file" class="form-control" id="input-image-url" @change="uploadImage">
  <div class="form-text">{{ imageUrl }}</div>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import {useStore} from 'vuex';
import UploadImageService from '@/upload_image/UploadImageService';

export default defineComponent({
  emits: ['update:modelValue'],
  name: 'UploadImage',
  props: ['modelValue'],
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
          context.emit('update:modelValue', imageUrl.value);
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