export default class MarketingInfoResponse {
    readonly newsletterStatus: string;

    constructor(newsletterStatus: string) {
        this.newsletterStatus = newsletterStatus;
    }
}