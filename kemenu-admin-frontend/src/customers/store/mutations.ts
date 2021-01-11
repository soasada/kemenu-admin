import {MutationTree} from 'vuex';
import CustomersState from '@/customers/store/CustomersState';
import CustomerResponse from '@/customers/CustomerResponse';

const customersMutations: MutationTree<CustomersState> = {
    EMPTY_CUSTOMERS(state: CustomersState) {
        state.customers = [];
    },
    PUSH_CUSTOMER(state: CustomersState, customer: CustomerResponse) {
        state.customers.push(customer);
    },
    SET_LOADING_CUSTOMERS(state: CustomersState, loadingCustomers: boolean) {
        state.loadingCustomers = loadingCustomers;
    }
};

export default customersMutations;