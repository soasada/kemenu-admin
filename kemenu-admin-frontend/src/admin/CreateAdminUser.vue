<template>
  <div class="create-admin-user">
    <BorderBottomTitle title="Create Admin"/>
    <AdminForm/>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import BorderBottomTitle from '@/layout/BorderBottomTitle.vue';
import AdminForm from '@/admin/AdminForm.vue';

export default defineComponent({
  name: 'CreateAdminUser',
  components: {
    BorderBottomTitle,
    AdminForm
  }
});
</script>

<style scoped lang="scss">

</style>
