<template>
  <div class="alert alert-danger kemenu-custom-alert" role="alert">
    {{ message }}
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';

export default defineComponent({
  name: 'Alert',
  props: {
    message: {
      type: String,
      required: true
    }
  }
});
</script>

<style scoped lang="scss">
.kemenu-custom-alert {
  position: absolute;
  top: 30px;
  width: 100%;
}
</style>