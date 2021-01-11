export default class SSEService {

    private constructor() {
        // Utility class, does not make sense to have an instance
    }

    static findAll<T>(endpoint: string, token: string, onMessage: (t: T) => void, onFinish: () => void): void {
        const eventSource = new EventSource(endpoint + '?authorization=Bearer%20' + token);

        eventSource.onmessage = (ev: MessageEvent) => {
            const response: T = JSON.parse(ev.data);
            onMessage(response);
        };

        eventSource.onerror = () => {
            eventSource.close();
            onFinish();
        };
    }
}