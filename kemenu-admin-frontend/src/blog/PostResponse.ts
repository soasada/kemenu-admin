export default class PostResponse {
    readonly id: string;
    readonly readableId: string;
    readonly title: string;
    readonly content: string;
    readonly locale: string;
    readonly createdBy: string;
    readonly updatedBy: string;
    readonly createdAt: string;
    readonly updatedAt: string;

    constructor(readableId: string, title: string, content: string, locale: string, createdBy: string, updatedBy: string, createdAt: string, updatedAt: string) {
        this.id = readableId;
        this.readableId = readableId;
        this.title = title;
        this.content = content;
        this.locale = locale;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}