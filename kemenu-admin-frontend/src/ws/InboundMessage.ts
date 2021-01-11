export default class InboundMessage {
    readonly service: string;
    readonly payload: string;

    constructor(service: string, payload: string) {
        this.service = service;
        this.payload = payload;
    }
}