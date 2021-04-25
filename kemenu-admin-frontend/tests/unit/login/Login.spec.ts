import {mount} from '@vue/test-utils';
import Login from '@/login/Login.vue';
import store from '@/store';
import router from '@/router';
import {createStore} from 'vuex';

describe('Login.vue', () => {
    beforeEach(() => {
        fetchMock.resetMocks();
    });

    it('Should load recaptcha and Sign In button should be disabled', () => {
        const wrapper = mount(Login, {
            global: {
                plugins: [store, router]
            }
        });

        expect(wrapper.find('#g-recaptcha').exists()).toBeTruthy();
        expect(wrapper.find('button').attributes('disabled')).toBe('');
    });

    it('Should enable Sign In button if user resolve the recaptcha', async () => {
        const wrapper = mount(Login, {
            global: {
                plugins: [store, router]
            }
        });

        // TODO: This is a hack until vue-test-utils 2 is released
        (wrapper.vm as any).recaptchaToken = 'TOKEN';
        (wrapper.vm as any).hasRecaptchaToken = true;
        await (wrapper.vm as any).$nextTick();

        expect(wrapper.find('button').attributes('disabled')).toBeUndefined();
    });

    it('Should show loading button when user clicks on Sign In', async () => {
        const store = createStore({});
        store.dispatch = jest.fn();

        const wrapper = mount(Login, {
            global: {
                plugins: [store, router]
            }
        });

        await wrapper.find('#inputEmail').setValue('example@example.com');
        await wrapper.find('#inputPassword').setValue('examplePassword');
        // TODO: This is a hack until vue-test-utils 2 is released
        (wrapper.vm as any).recaptchaToken = 'TOKEN';
        (wrapper.vm as any).hasRecaptchaToken = true;
        await (wrapper.vm as any).$nextTick();
        await wrapper.find('form').trigger('submit.prevent');
        await (wrapper.vm as any).$nextTick();

        expect((wrapper.vm as any).loading).toBeTruthy();
    });
});