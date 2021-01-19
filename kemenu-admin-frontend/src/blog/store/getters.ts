import BlogState from '@/blog/store/BlogState';
import {GetterTree} from 'vuex';
import BlogResponse from '@/blog/BlogResponse';
import PostResponse from '@/blog/PostResponse';

const blogGetters: GetterTree<BlogState, any> = {
    getAllBlogs(state: BlogState): BlogResponse[] {
        return state.blogs;
    },
    findBlog(state: BlogState): (id: string) => BlogResponse | undefined {
        return (id: string) => {
            return state.blogs.find(b => b.id === id);
        };
    },
    findBlogPost(state: BlogState): (blogId: string, readableId: string) => PostResponse | undefined {
        return (blogId: string, readableId: string) => {
            return state.blogs.find(b => b.id == blogId)?.posts.get(readableId);
        }
    },
    isLoadingBlogs(state: BlogState): boolean {
        return state.loadingBlogs;
    }
};

export default blogGetters;