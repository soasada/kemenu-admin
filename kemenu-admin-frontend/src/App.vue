<template>
  <div id="authView" v-if="store.getters.isAuthenticated">
    <NavbarSection/>
    <div class="container-fluid">
      <div class="row">
        <SidebarSection/>
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
          <router-view/>
        </main>
      </div>
    </div>
  </div>

  <div v-else>
    <router-view/>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import {useStore} from 'vuex';
import NavbarSection from '@/layout/NavbarSection.vue';
import SidebarSection from '@/layout/SidebarSection.vue';

export default defineComponent({
  name: 'App',
  components: {
    NavbarSection,
    SidebarSection
  },
  setup() {
    const store = useStore();

    return {store};
  }
});
</script>

<style lang="scss">
body {
  font-size: .875rem;
}

.feather {
  width: 16px;
  height: 16px;
  vertical-align: text-bottom;
}
</style>
