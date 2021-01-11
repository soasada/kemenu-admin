import BusinessResponse from '@/customers/BusinessResponse';
import MarketingInfoResponse from '@/customers/MarketingInfoResponse';

export default class CustomerResponse {
    readonly id: string;
    readonly email: string;
    readonly password: string;
    readonly businesses: BusinessResponse[];
    readonly role: string;
    readonly marketingInfo: MarketingInfoResponse;
    readonly createdAt: string;
    readonly updatedAt: string;

    constructor(id: string, email: string, password: string, businesses: BusinessResponse[], role: string, marketingInfo: MarketingInfoResponse, createdAt: string, updatedAt: string) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.businesses = businesses;
        this.role = role;
        this.marketingInfo = marketingInfo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}