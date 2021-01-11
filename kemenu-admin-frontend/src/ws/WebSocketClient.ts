import OutboundMessage from './OutboundMessage';
import InboundMessage from './InboundMessage';

class WebSocketClient {
    private static INSTANCE: WebSocketClient;

    private open: boolean;
    private socket: WebSocket;
    private listeners: Map<string, Array<(payload: string) => void>>;

    private constructor() {
        this.open = false;
        this.socket = this.connect(0);
        this.listeners = new Map<string, Array<(payload: string) => void>>();
    }

    private connect(cont: number): WebSocket {
        if (cont <= 14) {
            this.socket = new WebSocket('ws://localhost:8086/main/flow');
            this.socket.onopen = () => {
                this.open = true;
            };

            this.socket.onmessage = (msg: MessageEvent) => {
                const responseMsg: OutboundMessage = JSON.parse(msg.data);
                const listenerList = this.listeners.get(responseMsg.service);
                if (listenerList) {
                    listenerList.forEach(listener => {
                        listener(responseMsg.payload);
                    });
                }
            };

            this.socket.onclose = () => {
                const newCont = ++cont;
                setTimeout(() => {
                    this.connect(newCont);
                }, 1000 * newCont);
            };
        } else {
            console.log('Please refresh your browser');
        }

        return this.socket;
    }

    static getInstance(): WebSocketClient {
        return this.INSTANCE || (this.INSTANCE = new this());
    }

    send(service: string, payload: string) {
        if (this.socket.readyState === WebSocket.OPEN && this.open) {
            const message = new InboundMessage(service, payload);
            this.socket.send(JSON.stringify(message));
        } else {
            console.log('Socket is not open ' + this.socket.readyState);
        }
    }

    on(service: string, listener: (payload: string) => void) {
        if (!this.listeners.has(service)) {
            this.listeners.set(service, []);
        }

        const listenerList = this.listeners.get(service);
        if (listenerList) listenerList.push(listener);
    }
}

const wsocket = WebSocketClient.getInstance();

export default wsocket;
