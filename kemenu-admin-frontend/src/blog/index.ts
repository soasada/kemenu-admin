import {RouteRecordRaw} from 'vue-router';

const blogRoutes: Array<RouteRecordRaw> = [
    {
        path: '/blog',
        name: 'Blog',
        component: () => import(/* webpackChunkName: "blog" */ './BlogView.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/blog/create',
        name: 'BlogCreate',
        component: () => import(/* webpackChunkName: "blogCreate" */ './BlogCreate.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/blog/update/:id',
        name: 'BlogUpdate',
        component: () => import(/* webpackChunkName: "blogUpdate" */ './BlogUpdate.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/blog/:id/post',
        name: 'BlogPostCreate',
        component: () => import(/* webpackChunkName: "blogPostCreate" */ './BlogPostCreate.vue'),
        meta: {requiresAuth: true}
    },
    {
        path: '/blog/:blogId/post/:postId/update',
        name: 'BlogPostUpdate',
        component: () => import(/* webpackChunkName: "blogPostUpdate" */ './BlogPostUpdate.vue'),
        meta: {requiresAuth: true}
    }
];

export default blogRoutes;
