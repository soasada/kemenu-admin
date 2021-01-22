import Identifiable from '@/crud/Identifiable';
import HttpClient from '@/http/HttpClient';

export default class CRUDService {

    private constructor() {
        // Utility class, does not make sense to have an instance
    }

    static async find<T>(endpoint: string, id: string, token: string): Promise<Response> {
        try {
            return await HttpClient.get(endpoint + '/' + id, token);
        } catch (e) {
            console.error(e);
            throw new Error(e);
        }
    }

    static async create<T extends object>(endpoint: string, newEntity: T, token: string): Promise<Response> {
        try {
            return await HttpClient.post(endpoint, newEntity, token);
        } catch (e) {
            console.error(e);
            throw new Error(e);
        }
    }

    static async update<T extends object>(id: string, endpoint: string, newEntity: T, token: string): Promise<Response> {
        try {
            return await HttpClient.put(endpoint + '/' + id, newEntity, token);
        } catch (e) {
            console.error(e);
            throw new Error(e);
        }
    }
}