import {mount} from '@vue/test-utils';
import UploadImage from '@/upload_image/UploadImage.vue';
import store from '@/store';
import UploadImageService from '@/upload_image/UploadImageService';
import {ref} from 'vue';

describe('UploadImage.vue', () => {
    it('Should emit the URL when an image is uploaded', async () => {
        const mockUpload = jest.fn();
        const expectedURL = 'https://example.com';
        mockUpload.mockReturnValueOnce(
            new Promise(
                (resolve) => {
                    resolve(expectedURL);
                }
            )
        );
        UploadImageService.upload = mockUpload;
        const imageUrl = ref('');
        const wrapper = mount(UploadImage, {
            props: {
                modelValue: imageUrl
            },
            global: {
                plugins: [store]
            }
        });
        const input = wrapper.find('input');
        await input.setValue(new File([''], 'example.png', {type: 'image/png'}));
        expect(wrapper.emitted()['update:modelValue']).toBeDefined();
    });
});