import {RouteRecordRaw} from 'vue-router';

const adminRoutes: Array<RouteRecordRaw> = [
    {
        path: '/admin/management',
        name: 'AdminManagement',
        component: () => import(/* webpackChunkName: "adminManagement" */ './AdminManagement.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/admin/create',
        name: 'CreateAdminUser',
        component: () => import(/* webpackChunkName: "createAdminUser" */ './CreateAdminUser.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/admin/user/:id',
        name: 'EditAdminUser',
        component: () => import(/* webpackChunkName: "editAdminUser" */ './EditAdminUser.vue'),
        meta: {requiresAuth: true}
    }
];

export default adminRoutes;
