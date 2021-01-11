import Utils from '../utils/Utils';

export default class HttpClient {

    private constructor() {
        // Utility class, does not make sense to have an instance
    }

    static get(resource: string, token = ''): Promise<Response> {
        return this.execute('GET', resource, {}, token);
    }

    static post<T extends object>(resource: string, body: T, token = ''): Promise<Response> {
        return this.execute('POST', resource, body, token);
    }

    static put<T extends object>(resource: string, body: T, token = ''): Promise<Response> {
        return this.execute('PUT', resource, body, token);
    }

    // this could be improved
    static delete(resource: string, id: string, token = ''): Promise<Response> {
        if (confirm('Are you sure? ID: ' + id)) {
            return this.execute('DELETE', resource + '/' + id, {}, token);
        } else {
            return new Promise<Response>(((resolve, reject) => reject('Cancelled')));
        }
    }

    private static async execute<T extends object>(method: string, resource: string, body: T, token: string): Promise<Response> {
        let headers;

        if (Utils.isBlankString(token)) {
            headers = {
                'Content-Type': 'application/json'
            };
        } else {
            headers = {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            };
        }

        let response: Response;
        if (Utils.isEmptyObject(body)) {
            response = await fetch(resource, {
                method: method,
                mode: 'cors',
                cache: 'no-cache',
                credentials: 'same-origin',
                headers: headers,
                redirect: 'follow',
                referrerPolicy: 'no-referrer'
            });
        } else {
            if (body instanceof FormData) {
                headers = {
                    'Authorization': 'Bearer ' + token
                };
                response = await fetch(resource, {
                    method: method,
                    mode: 'cors',
                    cache: 'no-cache',
                    credentials: 'same-origin',
                    headers: headers,
                    redirect: 'follow',
                    referrerPolicy: 'no-referrer',
                    body: body
                });
            } else {
                response = await fetch(resource, {
                    method: method,
                    mode: 'cors',
                    cache: 'no-cache',
                    credentials: 'same-origin',
                    headers: headers,
                    redirect: 'follow',
                    referrerPolicy: 'no-referrer',
                    body: JSON.stringify(body)
                });
            }
        }

        if (response.ok) {
            return response;
        }

        throw new Error('Response status is not 2XX, is: ' + response.status);
    }
}