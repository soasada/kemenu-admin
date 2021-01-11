<template>
  <div class="admin-user" v-if="!loading">
    <BorderBottomTitle :title="'Editing Admin: ' + admin.email"/>
    <AdminForm :admin="admin"/>
  </div>
  <div class="admin-user" v-else>
    <div class="text-center spinner-border" style="width: 3rem; height: 3rem;" role="status"></div>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import {useStore} from 'vuex';
import {useRoute} from 'vue-router';
import HttpClient from '@/http/HttpClient';
import AdminResponse from '@/admin/AdminResponse';
import BorderBottomTitle from '@/layout/BorderBottomTitle.vue';
import AdminForm from '@/admin/AdminForm.vue';

export default defineComponent({
  name: 'EditAdminUser',
  components: {
    BorderBottomTitle,
    AdminForm
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    const adminId = route.params.id;
    const loading = ref(true);
    const admin = ref<AdminResponse>();

    HttpClient.get('/v1/admins/' + adminId, store.getters.getAccessToken).then((response: Response) => {
      response.json().then((adminResponse: AdminResponse) => {
        admin.value = adminResponse;
        loading.value = false;
      }).catch((error) => {
        console.log(error);
        loading.value = false;
      });
    });

    return {loading, admin};
  }
});
</script>

<style scoped lang="scss">

</style>
