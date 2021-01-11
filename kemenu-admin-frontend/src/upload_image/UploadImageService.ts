import HttpClient from '@/http/HttpClient';

export default class UploadImageService {
    private static ENDPOINT = '/v1/upload/image';

    private constructor() {
        // Utility class, does not make sense to have an instance
    }

    static async upload(imageFile: File, token: string): Promise<string> {
        const formData = new FormData();
        formData.append('file', imageFile, imageFile.name);
        const response = await HttpClient.post(UploadImageService.ENDPOINT, formData, token);
        const json = await response.json();
        return json.url;
    }
}