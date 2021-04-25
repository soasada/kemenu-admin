<template>
  <div
      id="g-recaptcha"
      class="g-recaptcha"
      :data-sitekey="sitekey">
  </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, watch} from 'vue';

export default defineComponent({
  emits: ['token'],
  name: 'Recaptcha',
  props: {
    shouldReset: {
      type: Boolean,
      required: true
    }
  },
  setup(props, context) {
    const sitekey = ref('6LeuEc0ZAAAAAFgpSVx07WdyDSDBi9pKIJTob2ir');
    const widgetId = ref(0);

    const win = window as any;

    const resetRecaptcha = () => {
      if (win.grecaptcha !== undefined) {
        win.grecaptcha.reset(widgetId.value);
      }
    };

    const renderRecaptcha = () => {
      try {
        widgetId.value = win.grecaptcha.render('g-recaptcha', {
          sitekey: sitekey.value,
          callback: (response: string) => {
            context.emit('token', response);
          }
        });
      } catch (e) {
        setTimeout(() => {
          renderRecaptcha();
        }, 1000);
      }
    };

    onMounted(() => {
      renderRecaptcha();
    });

    watch(() => props.shouldReset, (shouldBeReset: boolean) => {
      if (shouldBeReset) {
        resetRecaptcha();
      }
    });

    return {sitekey};
  }
});
</script>

<style scoped>
.g-recaptcha {
  padding-top: 15px;
  padding-bottom: 15px;
}
</style>