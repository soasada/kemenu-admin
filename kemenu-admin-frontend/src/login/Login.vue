<template>
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="signIn">
      <div v-if="signInErrorMsg" class="alert alert-danger" role="alert">
        {{ signInErrorMsg }}
      </div>

      <img class="mb-4" src="../assets/logo.png" alt="Kemenu Logo" width="72" height="72">
      <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>

      <label for="inputEmail" class="visually-hidden">Email address</label>
      <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required="" autofocus=""
             v-model="username">

      <label for="inputPassword" class="visually-hidden">Password</label>
      <input type="password" id="inputPassword" class="form-control" placeholder="Password" required=""
             v-model="password">

      <Recaptcha :should-reset="loading" @token="onToken($event)"/>

      <div class="d-grid gap-2 col-6 mx-auto">
        <button class="btn btn-lg btn-primary" type="submit" :disabled="!hasRecaptchaToken || loading">
          <span v-if="!loading">Sign in</span>
          <span v-else>
          <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
          Loading...
        </span>
        </button>
      </div>

      <p class="mt-5 mb-3 text-muted">© {{ new Date().getFullYear() }}</p>

    </form>
  </div>
</template>

<script lang="ts">
import {computed, defineComponent, ref, watch} from 'vue';
import {useStore} from 'vuex';
import {useRoute, useRouter} from 'vue-router';
import Recaptcha from '@/recaptcha/Recaptcha.vue';
import Utils from '@/utils/Utils';

export default defineComponent({
  name: 'Login',
  components: {
    Recaptcha,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const route = useRoute();

    const username = ref('');
    const password = ref('');
    const recaptchaToken = ref('');
    const hasRecaptchaToken = ref(false);
    const loading = ref(false);

    const signIn = () => {
      loading.value = true;
      hasRecaptchaToken.value = false;
      store.dispatch('cleanSignInError');

      store.dispatch('signIn', {
        username: username.value,
        password: password.value,
        recaptchaToken: recaptchaToken.value,
        router: router,
        route: route
      });
    };

    const onToken = (token: string) => {
      recaptchaToken.value = token;
      hasRecaptchaToken.value = true;
    };

    const signInErrorMsg = computed(() => {
      return store.getters.getSignInErrorMsg;
    });

    watch(signInErrorMsg, (signInErrorMsg: string) => {
      loading.value = Utils.isBlankString(signInErrorMsg);
    });

    return {username, password, recaptchaToken, hasRecaptchaToken, loading, signIn, onToken, signInErrorMsg};
  }
});
</script>

<style scoped>
html,
body {
  height: 100%;
}

body {
  display: flex;
  align-items: center;
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #f5f5f5;
}

.form-signin {
  width: 100%;
  max-width: 330px;
  padding: 15px;
  margin: auto;
}

.form-signin {
  font-weight: 400;
}

.form-signin .form-control {
  position: relative;
  box-sizing: border-box;
  height: auto;
  padding: 10px;
  font-size: 16px;
}

.form-signin .form-control:focus {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

</style>