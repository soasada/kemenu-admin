import MenuResponse from '@/customers/MenuResponse';

export default class BusinessResponse {
    readonly id: string;
    readonly name: string;
    readonly menus: MenuResponse[];
    readonly imageUrl: string;
    readonly phone: string;
    readonly info: string;
    readonly color: string;

    constructor(id: string, name: string, menus: MenuResponse[], imageUrl: string, phone: string, info: string, color: string) {
        this.id = id;
        this.name = name;
        this.menus = menus;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.info = info;
        this.color = color;
    }
}