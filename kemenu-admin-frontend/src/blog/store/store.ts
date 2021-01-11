import {Module} from 'vuex';
import BlogState from '@/blog/store/BlogState';
import blogMutations from '@/blog/store/mutations';
import {blogActions} from '@/blog/store/actions';
import blogGetters from '@/blog/store/getters';

const state: BlogState = {
    blogs: [],
    loadingBlogs: true
};

const blogStore: Module<BlogState, any> = {
    state,
    mutations: blogMutations,
    actions: blogActions,
    getters: blogGetters
};

export default blogStore;