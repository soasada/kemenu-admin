import AllergenResponse from '@/customers/AllergenResponse';

export default class DishResponse {
    readonly name: string;
    readonly description: string;
    readonly price: number;
    readonly allergens: AllergenResponse[];
    readonly imageUrl: string;
    readonly available: boolean;

    constructor(name: string, description: string, price: number, allergens: AllergenResponse[], imageUrl: string, available: boolean) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.allergens = allergens;
        this.imageUrl = imageUrl;
        this.available = available;
    }
}