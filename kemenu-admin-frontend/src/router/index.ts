import {createRouter, createWebHashHistory, RouteRecordRaw} from 'vue-router';
import blog from '@/blog';
import customers from '@/customers';
import admin from '@/admin';

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
    }
];

const finalRoutes = routes
    .concat(blog)
    .concat(customers)
    .concat(admin);

const router = createRouter({
    history: createWebHashHistory(),
    routes: finalRoutes
});

export default router;
