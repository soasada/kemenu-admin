import BlogState from '@/blog/store/BlogState';
import {findAllBlogs} from '@/blog/store/actions';
import SSEService from '@/sse/SSEService';
import store from '@/store';
import {mockActionContext} from '../../store_utils';

describe('actions.ts', () => {

    const state: BlogState = {
        blogs: [],
        loadingBlogs: true
    };

    it('Should call to findAll SSE method if empty blog list and loadingBlogs is true', () => {
        const mockSSE = jest.fn();
        SSEService.findAll = mockSSE;

        const findAllBlogsBound = findAllBlogs.bind(store);
        const actionContext = mockActionContext(state);

        findAllBlogsBound(actionContext);
        expect(mockSSE.mock.calls.length).toBe(1);
    });
});