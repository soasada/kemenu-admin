import BlogState from '@/blog/store/BlogState';
import {randomBlogPost} from '../BlogResponseHelper';
import blogMutations from '@/blog/store/mutations';
import blogGetters from '@/blog/store/getters';

describe('getters.ts', () => {

    const state: BlogState = {
        blogs: [],
        loadingBlogs: true
    };

    it('Should find a blog if exists', () => {
        let blog = randomBlogPost();
        blogMutations.PUSH_BLOG(state, blog);

        const foundBlog = blogGetters.findBlog(state, {}, {}, {})(blog.id);

        expect(blog.id).toBe(foundBlog.id);
    });
});