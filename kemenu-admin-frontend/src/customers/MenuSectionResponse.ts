import DishResponse from '@/customers/DishResponse';

export default class MenuSectionResponse {
    readonly name: string;
    readonly dishes: DishResponse[];

    constructor(name: string, dishes: DishResponse[]) {
        this.name = name;
        this.dishes = dishes;
    }
}