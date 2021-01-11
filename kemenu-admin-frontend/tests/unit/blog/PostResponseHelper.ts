import PostResponse from '@/blog/PostResponse';
import chance from '../chance';

export function randomReadableId(): string {
    return chance.word() + '-' + chance.word() + '-' + chance.word();
}

export function randomPost(): PostResponse {
    return new PostResponse(
        randomReadableId(),
        chance.sentence(),
        chance.paragraph(),
        chance.locale(),
        chance.email(),
        chance.email(),
        chance.date().toISOString(),
        chance.date().toISOString()
    );
}