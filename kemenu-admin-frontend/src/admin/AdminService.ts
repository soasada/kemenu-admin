import AdminResponse from '@/admin/AdminResponse';
import HttpClient from '@/http/HttpClient';

export default class AdminService {
    private static ENDPOINT = '/v1/admins';

    private constructor() {
        // Utility class, does not make sense to have an instance
    }

    static async findAll(token: string): Promise<AdminResponse[]> {
        const response = await HttpClient.get(AdminService.ENDPOINT, token);
        return await response.json();
    }
}