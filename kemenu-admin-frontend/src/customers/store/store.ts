import {Module} from 'vuex';
import CustomersState from '@/customers/store/CustomersState';
import customersMutations from '@/customers/store/mutations';
import {customersActions} from '@/customers/store/actions';
import customersGetters from '@/customers/store/getters';

const state: CustomersState = {
    customers: [],
    loadingCustomers: true
};

const customersStore: Module<CustomersState, any> = {
    state,
    mutations: customersMutations,
    actions: customersActions,
    getters: customersGetters
};

export default customersStore;