import PostResponse from '@/blog/PostResponse';

export default class BlogResponse {
    readonly id: string;
    readonly imageUrl: string;
    readonly posts: Map<string, PostResponse>;
    readonly createdAt: string;
    readonly updatedAt: string;

    constructor(id: string, imageUrl: string, posts: Map<string, PostResponse>, createdAt: string, updatedAt: string) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.posts = posts;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}