import BlogState from '@/blog/store/BlogState';
import blogMutations from '@/blog/store/mutations';
import {randomBlogPost} from '../BlogResponseHelper';

describe('mutations.ts', () => {

    const state: BlogState = {
        blogs: [],
        loadingBlogs: true
    };

    it('Should empty a blog', () => {
        blogMutations.PUSH_BLOG(state, randomBlogPost());
        expect(state.blogs.length).toBe(1);
        blogMutations.EMPTY_BLOGS(state);
        expect(state.blogs.length).toBe(0);
    });

    it('Should set loadingBlogs', () => {
        blogMutations.SET_LOADING_BLOGS(state, false);
        expect(state.loadingBlogs).toBeFalsy();
    });
});