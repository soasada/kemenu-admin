import BlogResponse from '@/blog/BlogResponse';
import {Router} from 'vue-router';
import CRUDService from '@/crud/CRUDService';
import BlogRequest from '@/blog/BlogRequest';
import {UnwrapRef} from 'vue';

export default class BlogService {
    private static ENDPOINT = '/v1/blog';

    private constructor() {
        // Utility class, does not make sense to have an instance
    }

    static create(newBlog: BlogRequest, token: string, onSuccess: () => void): void {
        CRUDService.create(BlogService.ENDPOINT, newBlog, token)
            .then(onSuccess)
            .catch(e => console.error(e));
    }

    static update(newBlog: BlogResponse, token: string, router: UnwrapRef<Router>): void {
        CRUDService.update(BlogService.ENDPOINT + '/' + newBlog.id, newBlog, token)
            .then(() => router.push('/blog'))
            .catch(e => console.error(e));
    }
}