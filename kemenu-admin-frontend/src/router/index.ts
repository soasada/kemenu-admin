import {createRouter, createWebHashHistory, RouteRecordRaw} from 'vue-router';
import blog from '@/blog';
import customers from '@/customers';

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'Login',
        component: () => import(/* webpackChunkName: "login" */ '../login/Login.vue')
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import(/* webpackChunkName: "dashboard" */ '../dashboard/Dashboard.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/admin/management',
        name: 'AdminManagement',
        component: () => import(/* webpackChunkName: "adminManagement" */ '../admin/AdminManagement.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/create/admin',
        name: 'CreateAdminUser',
        component: () => import(/* webpackChunkName: "createAdminUser" */ '../admin/CreateAdminUser.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/admin/user/:id',
        name: 'EditAdminUser',
        component: () => import(/* webpackChunkName: "editAdminUser" */ '../admin/EditAdminUser.vue'),
        meta: {requiresAuth: true}
    }
];

const finalRoutes = routes
    .concat(blog)
    .concat(customers);

const router = createRouter({
    history: createWebHashHistory(),
    routes: finalRoutes
});

export default router;
