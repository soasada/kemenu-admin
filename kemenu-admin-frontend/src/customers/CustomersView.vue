<template>
  <div class="customers">
    <BorderBottomTitle title="Customers"/>
    <ResponsiveTable
        :column-names="['email', 'role', 'marketingInfo.newsletterStatus', 'createdAt', 'updatedAt']"
        :table-data="store.getters.getAllCustomers"
        :show-actions-column="store.getters.isSuperAdmin || store.getters.isAdmin"
        :loading-data="store.getters.isLoadingCustomers"
        @edit="onEdit"
        @delete="onDelete"
    />
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import {useStore} from 'vuex';
import BorderBottomTitle from '@/layout/BorderBottomTitle.vue';
import ResponsiveTable from '@/responsive_table/ResponsiveTable.vue';
import CustomerResponse from '@/customers/CustomerResponse';

export default defineComponent({
  name: 'CustomersView',
  components: {
    BorderBottomTitle,
    ResponsiveTable
  },
  setup() {
    const store = useStore();

    store.dispatch('findAllCustomers');

    const onEdit = (customer: CustomerResponse) => {
      console.log('Editing: ' + customer.email);
    };

    const onDelete = (customer: CustomerResponse) => {
      console.log('Deleting: ' + customer.email);
    };

    return {store, onEdit, onDelete};
  }
});
</script>
