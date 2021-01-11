import {createStore} from 'vuex';
import Utils from '@/utils/Utils';
import HttpClient from '@/http/HttpClient';
import jwtDecode from 'jwt-decode';
import JWT from '@/login/JWT';
import Roles from '@/login/Roles';
import blogStore from '@/blog/store/store';
import customersStore from '@/customers/store/store';

type State = {
    accessToken: string;
    refreshToken: string;
    signInErrorMsg: string;
    roles: Array<string>;
};

const state: State = {
    accessToken: '',
    refreshToken: '',
    signInErrorMsg: '',
    roles: []
};

export default createStore({
    state,
    mutations: {
        SET_ACCESS_TOKEN(state: State, accessToken: string) {
            state.accessToken = accessToken;
        },
        SET_REFRESH_TOKEN(state: State, refreshToken: string) {
            state.refreshToken = refreshToken;
        },
        SET_SIGN_IN_ERROR_MSG(state: State, signInErrorMsg: string) {
            state.signInErrorMsg = signInErrorMsg;
        },
        SET_ROLES(state: State, roles: Array<string>) {
            state.roles = roles;
        },
        PUSH_ROLE(state: State, role: string) {
            state.roles.push(role);
        }
    },
    actions: {
        signIn({commit}, {username, password, recaptchaToken, router, route}) {
            HttpClient.post('/v1/login', {
                username: username,
                password: password,
                recaptchaToken: recaptchaToken
            }).then((response: Response) => {
                if (response.ok) {
                    const accessToken = response.headers.get('Authorization');
                    if (accessToken === null) throw new Error('No JWT header');
                    const refreshToken = response.headers.get('JWT-Refresh-Token');
                    if (refreshToken === null) throw new Error('No Refresh token header');

                    const decodedAccessToken = jwtDecode(accessToken) as JWT;

                    commit('SET_ACCESS_TOKEN', accessToken);
                    commit('SET_REFRESH_TOKEN', refreshToken);
                    commit('SET_SIGN_IN_ERROR_MSG', '');

                    decodedAccessToken.roles.forEach((role: string) => {
                        commit('PUSH_ROLE', role);
                    });

                    if (route.query.redirect) {
                        router.push(route.query.redirect);
                    } else {
                        router.push('/dashboard');
                    }
                } else {
                    throw new Error('Http error: ' + response.status);
                }
            }).catch(() => {
                commit('SET_ACCESS_TOKEN', '');
                commit('SET_REFRESH_TOKEN', '');
                commit('SET_SIGN_IN_ERROR_MSG', 'Incorrect username or password');
                commit('SET_ROLES', []);
                router.push('/');
            });
        },
        cleanSignInError({commit}) {
            commit('SET_SIGN_IN_ERROR_MSG', '');
        },
        signOut({commit}) {
            commit('SET_ACCESS_TOKEN', '');
            commit('SET_REFRESH_TOKEN', '');
            commit('SET_SIGN_IN_ERROR_MSG', '');
            commit('SET_ROLES', []);
        }
    },
    getters: {
        isAuthenticated(state: State): boolean {
            return !Utils.isBlankString(state.accessToken)
                && !Utils.isBlankString(state.refreshToken)
                && state.roles.length > 0;
        },
        getRoles(state: State): Array<string> {
            return state.roles;
        },
        getAccessToken(state: State): string {
            return state.accessToken;
        },
        getSignInErrorMsg(state: State): string {
            return state.signInErrorMsg;
        },
        isSuperAdmin(state: State): boolean {
            return state.roles.includes(Roles.SUPER_ADMIN);
        },
        isAdmin(state: State): boolean {
            return state.roles.includes(Roles.ADMIN);
        },
        isModerator(state: State): boolean {
            return state.roles.includes(Roles.MODERATOR);
        }
    },
    modules: {
        blogStore: blogStore,
        customersStore: customersStore
    }
});
