import {ActionContext} from 'vuex';

export function mockActionContext<T>(state: T): ActionContext<T, any> {
    return {
        dispatch: jest.fn(),
        commit: jest.fn(),
        state: state,
        getters: {},
        rootState: {},
        rootGetters: {}
    };
}