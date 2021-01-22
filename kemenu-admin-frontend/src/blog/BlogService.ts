import CRUDService from '@/crud/CRUDService';
import BlogRequest from '@/blog/BlogRequest';

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

    static createPost(id: string, newBlog: BlogRequest, token: string, onSuccess: () => void): void {
        CRUDService.create(BlogService.ENDPOINT + '/' + id + '/post', newBlog, token)
            .then(onSuccess)
            .catch(e => console.error(e));
    }

    static update(blogId: string, newBlog: BlogRequest, token: string, onSuccess: () => void): void {
        CRUDService.update(blogId, BlogService.ENDPOINT, newBlog, token)
            .then(onSuccess)
            .catch(e => console.error(e));
    }
}