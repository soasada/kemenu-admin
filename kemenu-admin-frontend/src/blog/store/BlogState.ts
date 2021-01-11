import BlogResponse from '@/blog/BlogResponse';

export default interface BlogState {
    blogs: BlogResponse[];
    loadingBlogs: boolean;
}