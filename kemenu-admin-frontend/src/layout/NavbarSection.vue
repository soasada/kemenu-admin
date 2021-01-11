<template>
  <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
    <router-link class="navbar-brand col-md-3 col-lg-2 me-0 px-3" to="/dashboard">
      Admin KEMENU - {{ version }}
    </router-link>
    <ul class="navbar-nav px-3">
      <li class="nav-item text-nowrap">
        <router-link class="nav-link" @click="signOut" to="/">Sign out</router-link>
      </li>
    </ul>
  </nav>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import {useStore} from 'vuex';
import HttpClient from '@/http/HttpClient';
import VersionResponse from '@/version/VersionResponse';

export default defineComponent({
  name: 'NavbarSection',
  setup() {
    const store = useStore();

    const version = ref('');

    HttpClient.get('/v1/app/version', store.getters.getAccessToken).then((response: Response) => {
      response.json().then((versionResponse: VersionResponse) => {
        version.value = versionResponse.version;
      }).catch((error) => {
        console.log(error);
      });
    });

    const signOut = () => {
      store.dispatch('signOut');
    };

    return {signOut, version};
  }
});
</script>

<style scoped lang="scss">
.navbar-brand {
  padding-top: .75rem;
  padding-bottom: .75rem;
  font-size: 1rem;
  background-color: rgba(0, 0, 0, .25);
  box-shadow: inset -1px 0 0 rgba(0, 0, 0, .25);
}

.navbar .navbar-toggler {
  top: .25rem;
  right: 1rem;
}
</style>
