import BlogResponse from '@/blog/BlogResponse';
import {useRouter} from 'vue-router';
import CRUDService from '@/crud/CRUDService';
import BlogRequest from '@/blog/BlogRequest';

export default class BlogService {
    private static ENDPOINT = '/v1/blog';

    private constructor() {
        // Utility class, does not make sense to have an instance
    }

    static create(newBlog: BlogRequest, token: string): void {
        CRUDService.create(BlogService.ENDPOINT, newBlog, token)
            .then(() => useRouter().push('/blog'))
            .catch(e => console.error(e));
    }

    static update(newBlog: BlogResponse, token: string): void {
        CRUDService.update(BlogService.ENDPOINT + '/' + newBlog.id, newBlog, token)
            .then(() => useRouter().push('/blog'))
            .catch(e => console.error(e));
    }
}