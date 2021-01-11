import BlogState from '@/blog/store/BlogState';
import {GetterTree} from 'vuex';
import BlogResponse from '@/blog/BlogResponse';

const blogGetters: GetterTree<BlogState, any> = {
    getAllBlogs(state: BlogState): BlogResponse[] {
        return state.blogs;
    },
    findBlog(state: BlogState): (id: string) => BlogResponse | undefined {
        return (id: string) => {
            return state.blogs.find(b => b.id === id);
        };
    },
    isLoadingBlogs(state: BlogState): boolean {
        return state.loadingBlogs;
    }
};

export default blogGetters;