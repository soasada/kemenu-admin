import {ActionContext, ActionHandler, ActionTree} from 'vuex';
import SSEService from '@/sse/SSEService';
import CustomersState from '@/customers/store/CustomersState';
import CustomerResponse from '@/customers/CustomerResponse';

export const findAllCustomers: ActionHandler<CustomersState, any> = (context: ActionContext<CustomersState, any>) => {
    if (context.state.loadingCustomers && context.state.customers.length === 0) {
        const onMessage = (customer: CustomerResponse) => {
            context.commit('PUSH_CUSTOMER', customer);
        };
        const onFinish = () => {
            context.commit('SET_LOADING_CUSTOMERS', false);
        };

        SSEService.findAll('/v1/customers', context.getters.getAccessToken, onMessage, onFinish);
    }
};

export const customersActions: ActionTree<CustomersState, any> = {
    findAllCustomers: findAllCustomers
};
