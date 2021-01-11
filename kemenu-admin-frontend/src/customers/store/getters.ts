import {GetterTree} from 'vuex';
import CustomersState from '@/customers/store/CustomersState';
import CustomerResponse from '@/customers/CustomerResponse';

const customersGetters: GetterTree<CustomersState, any> = {
    getAllCustomers(state: CustomersState): CustomerResponse[] {
        return state.customers;
    },
    findCustomer(state: CustomersState): (id: string) => CustomerResponse | undefined {
        return (id: string) => {
            return state.customers.find(b => b.id === id);
        };
    },
    isLoadingCustomers(state: CustomersState): boolean {
        return state.loadingCustomers;
    }
};

export default customersGetters;