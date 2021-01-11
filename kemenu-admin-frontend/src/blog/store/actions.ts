import BlogState from '@/blog/store/BlogState';
import {ActionContext, ActionHandler, ActionTree} from 'vuex';
import BlogResponse from '@/blog/BlogResponse';
import SSEService from '@/sse/SSEService';

export const findAllBlogs: ActionHandler<BlogState, any> = (context: ActionContext<BlogState, any>) => {
    if (context.state.loadingBlogs && context.state.blogs.length === 0) {
        const onMessage = (blog: BlogResponse) => {
            context.commit('PUSH_BLOG', blog);
        };
        const onFinish = () => {
            context.commit('SET_LOADING_BLOGS', false);
        };

        SSEService.findAll('/v1/blog', context.getters.getAccessToken, onMessage, onFinish);
    }
};

export const blogActions: ActionTree<BlogState, any> = {
    findAllBlogs: findAllBlogs
};
