import ErrorMessage from './ErrorMessage';

export default class OutboundMessage {
    readonly service: string;
    readonly payload: string;
    readonly error: ErrorMessage;

    constructor(service: string, payload: string, error: ErrorMessage) {
        this.service = service;
        this.payload = payload;
        this.error = error;
    }
}