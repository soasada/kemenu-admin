import MenuSectionResponse from '@/customers/MenuSectionResponse';

export default class MenuResponse {
    readonly id: string;
    readonly sections: MenuSectionResponse[];
    readonly imageUrl: string;
    readonly currency: string;
    readonly name: string;

    constructor(id: string, sections: MenuSectionResponse[], imageUrl: string, currency: string, name: string) {
        this.id = id;
        this.sections = sections;
        this.imageUrl = imageUrl;
        this.currency = currency;
        this.name = name;
    }
}