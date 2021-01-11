<template>
  <div class="adminManagement" v-if="store.getters.isSuperAdmin">
    <BorderBottomTitle title="Admin Management"/>
    <button type="button" class="btn btn-primary btn-lg mb-5" @click="createAdmin">Create Admin</button>
    <ResponsiveTable
        :column-names="['id', 'email', 'role', 'createdAt', 'updatedAt']"
        :table-data="admins"
        :show-actions-column="store.getters.isSuperAdmin"
        :loading-data="loading"
        @edit="onEdit"
        @delete="onDelete"
    />
  </div>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import {useStore} from 'vuex';
import {useRouter} from 'vue-router';
import BorderBottomTitle from '@/layout/BorderBottomTitle.vue';
import ResponsiveTable from '@/responsive_table/ResponsiveTable.vue';
import HttpClient from '@/http/HttpClient';
import AdminResponse from '@/admin/AdminResponse';

export default defineComponent({
  name: 'AdminManagement',
  components: {
    BorderBottomTitle,
    ResponsiveTable
  },
  setup() {
    const store = useStore();
    const admins = ref<AdminResponse[]>([]);
    const loading = ref(true);
    const router = useRouter();

    HttpClient.get('/v1/admins', store.getters.getAccessToken).then((response: Response) => {
      response.json().then((adminResponse: AdminResponse[]) => {
        admins.value = adminResponse;
        loading.value = false;
      }).catch((error) => {
        console.log(error);
        loading.value = false;
      });
    });

    const onEdit = (admin: AdminResponse) => {
      router.push('/admin/user/' + admin.id);
    };

    const onDelete = (admin: AdminResponse) => {
      console.log('Deleting: ' + admin.email);
    };

    const createAdmin = () => {
      router.push('/create/admin');
    }

    return {store, admins, loading, onEdit, onDelete, createAdmin};
  }
});
</script>
