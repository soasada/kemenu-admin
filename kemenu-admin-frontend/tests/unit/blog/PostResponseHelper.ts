import PostResponse from '@/blog/PostResponse';
import chance from '../chance';

export function randomReadableId(): string {
    return chance.word() + '-' + chance.word() + '-' + chance.word();
}

export function randomPost(): PostResponse {
    return {
        readableId: randomReadableId(),
        title: chance.sentence(),
        content: chance.paragraph(),
        locale: chance.locale(),
        createdBy: chance.email(),
        updatedBy: chance.email(),
        createdAt: chance.date().toISOString(),
        updatedAt: chance.date().toISOString()
    };
}