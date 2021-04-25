<template>
  <div class="adminManagement" v-if="store.getters.isSuperAdmin">
    <BorderBottomTitle title="Admin Management"/>
    <button type="button" class="btn btn-primary btn-lg mb-5" @click="createAdmin">Create Admin</button>
    <DataTable :value="admins" :loading="loading" v-model:filters="filters" :paginator="true" :rows="10" responsiveLayout="scroll">
      <template #header>
        <div class="p-d-flex p-jc-between">
          <span class="p-input-icon-left">
            <i class="pi pi-search"/>
            <InputText v-model="filters['global'].value" placeholder="Search"/>
          </span>
        </div>
      </template>
      <Column v-for="col of columns"
              :field="col.$props.field"
              :header="col.$props.header"
              :key="col.$props.field">
      </Column>
      <Column :exportable="false">
        <template #body="slotProps">
          <Button icon="pi pi-pencil" class="p-button-rounded p-button-success p-mr-2" @click="onEdit(slotProps.data)" />
          <Button icon="pi pi-trash" class="p-button-rounded p-button-warning" @click="onDelete(slotProps.data)" />
        </template>
      </Column>
    </DataTable>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import {useStore} from 'vuex';
import {useRouter} from 'vue-router';
import BorderBottomTitle from '@/layout/BorderBottomTitle.vue';
import HttpClient from '@/http/HttpClient';
import AdminResponse from '@/admin/AdminResponse';
import AdminService from '@/admin/AdminService';
import Column from 'primevue/column';
import {FilterMatchMode} from 'primevue/api';

export default defineComponent({
  name: 'AdminManagement',
  components: {
    BorderBottomTitle
  },
  setup() {
    const store = useStore();
    const admins = ref<AdminResponse[]>([]);
    const loading = ref(true);
    const router = useRouter();
    const columns = ref<Column[]>([
      {$props: {field: 'id', header: 'ID'}},
      {$props: {field: 'email', header: 'Email'}},
      {$props: {field: 'role', header: 'Role'}},
      {$props: {field: 'createdAt', header: 'Created At'}},
      {$props: {field: 'updatedAt', header: 'Updated At'}},
    ]);
    const filters = ref({
      'global': {value: null, matchMode: FilterMatchMode.CONTAINS}
    });

    AdminService.findAll(store.getters.getAccessToken)
        .then(response => {
          admins.value = response;
          loading.value = false;
        })
        .catch(error => {
          console.log(error);
          loading.value = false;
        });

    const onEdit = (admin: AdminResponse) => {
      router.push('/admin/user/' + admin.id);
    };

    const onDelete = (admin: AdminResponse) => {
      HttpClient.delete('/v1/admins', admin.id, store.getters.getAccessToken)
          .then(() => {
            loading.value = true;
            AdminService.findAll(store.getters.getAccessToken)
                .then(response => {
                  admins.value = response;
                  loading.value = false;
                })
                .catch(error => {
                  console.log(error);
                  loading.value = false;
                });
          })
          .catch(error => {
            console.log(error);
          });
    };

    const createAdmin = () => {
      router.push({name: 'CreateAdminUser'});
    }

    return {store, admins, loading, columns, filters, onEdit, onDelete, createAdmin};
  }
});
</script>
