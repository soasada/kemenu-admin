<template>
  <div class="customers">
    <BorderBottomTitle title="Customers"/>
    <DataTable :value="store.getters.getAllCustomers" v-model:filters="filters" :paginator="true" :rows="10" responsiveLayout="scroll">
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
    </DataTable>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import {useStore} from 'vuex';
import BorderBottomTitle from '@/layout/BorderBottomTitle.vue';
import CustomerResponse from '@/customers/CustomerResponse';
import Column from 'primevue/column';
import {FilterMatchMode} from 'primevue/api';

export default defineComponent({
  name: 'CustomersView',
  components: {
    BorderBottomTitle
  },
  setup() {
    const store = useStore();
    const columns = ref<Column[]>([
      {$props: {field: 'email', header: 'Email'}},
      {$props: {field: 'businesses.0.name', header: 'Restaurant'}},
      {$props: {field: 'role', header: 'Role'}},
      {$props: {field: 'marketingInfo.newsletterStatus', header: 'Newsletter?'}},
      {$props: {field: 'createdAt', header: 'Created At'}},
      {$props: {field: 'updatedAt', header: 'Updated At'}},
    ]);

    const filters = ref({
      'global': {value: null, matchMode: FilterMatchMode.CONTAINS}
    });

    store.dispatch('findAllCustomers');

    const onEdit = (customer: CustomerResponse) => {
      console.log('Editing: ' + customer.email);
    };

    const onDelete = (customer: CustomerResponse) => {
      console.log('Deleting: ' + customer.email);
    };

    return {store, columns, filters, onEdit, onDelete};
  }
});
</script>
