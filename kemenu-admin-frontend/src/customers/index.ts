import {RouteRecordRaw} from 'vue-router';

const customersRoutes: Array<RouteRecordRaw> = [
    {
        path: '/customers',
        name: 'Customers',
        component: () => import(/* webpackChunkName: "customers" */ './CustomersView.vue'),
        meta: {requiresAuth: true}
    },
];

export default customersRoutes;
