import BlogState from '@/blog/store/BlogState';
import {MutationTree} from 'vuex';
import BlogResponse from '@/blog/BlogResponse';

const blogMutations: MutationTree<BlogState> = {
    EMPTY_BLOGS(state: BlogState) {
        state.blogs = [];
    },
    PUSH_BLOG(state: BlogState, blog: BlogResponse) {
        state.blogs.push(blog);
    },
    SET_LOADING_BLOGS(state: BlogState, loadingBlogs: boolean) {
        state.loadingBlogs = loadingBlogs;
    }
};

export default blogMutations;