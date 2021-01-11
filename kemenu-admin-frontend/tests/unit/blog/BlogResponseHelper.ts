import BlogResponse from '@/blog/BlogResponse';
import {randomPost} from './PostResponseHelper';
import chance from '../chance';
import PostResponse from '@/blog/PostResponse';

export function randomBlogPost(): BlogResponse {
    const post1 = randomPost();
    let post2;
    do {
        post2 = randomPost();
    } while (post2.locale == post1.locale);
    const posts = new Map<string, PostResponse>();

    posts.set(post1.locale, post1);
    posts.set(post2.locale, post2);

    return new BlogResponse(
        chance.guid(),
        chance.url(),
        posts,
        chance.date().toISOString(),
        chance.date().toISOString()
    );
}