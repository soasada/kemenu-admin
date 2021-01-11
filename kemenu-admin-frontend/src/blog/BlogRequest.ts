export default class BlogRequest {
    readonly title: string;
    readonly content: string;
    readonly imageUrl: string;
    readonly locale: string;

    constructor(title: string, content: string, imageUrl: string, locale: string) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.locale = locale;
    }
}