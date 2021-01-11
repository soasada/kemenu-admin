<template>
  <div id="admin-form">
    <form @submit.prevent="sendForm">
      <div class="mb-3">
        <label for="inputId" class="form-label">ID</label>
        <input type="text" class="form-control" id="inputId" v-model="adminForm.id" disabled>
      </div>
      <div class="mb-3">
        <label for="inputEmail" class="form-label">Email address</label>
        <input type="email" class="form-control" id="inputEmail" v-model="adminForm.email">
      </div>
      <div class="mb-3">
        <label for="inputPassword" class="form-label">Password</label>
        <input type="password" class="form-control" id="inputPassword" v-model="adminForm.password">
      </div>
      <div class="mb-3">
        <label for="inputRole" class="form-label">Role</label>
        <select id="inputRole" class="form-select" v-model="adminForm.role">
          <option value="SUPER_ADMIN">SUPER_ADMIN</option>
          <option value="ADMIN">ADMIN</option>
          <option value="MODERATOR">MODERATOR</option>
        </select>
      </div>
      <div class="mb-3">
        <label for="inputCreatedAt" class="form-label">Created At</label>
        <input type="text" class="form-control" id="inputCreatedAt" v-model="adminForm.createdAt" disabled>
      </div>
      <div class="mb-3">
        <label for="inputUpdatedAt" class="form-label">Updated At</label>
        <input type="text" class="form-control" id="inputUpdatedAt" v-model="adminForm.updatedAt" disabled>
      </div>
      <button type="submit" class="btn btn-primary">{{ adminForm.id ? 'Update' : 'Create' }}</button>
    </form>
  </div>
</template>

<script lang="ts">
import {defineComponent, PropType} from 'vue';
import AdminResponse from '@/admin/AdminResponse';
import HttpClient from '@/http/HttpClient';
import {useStore} from 'vuex';
import {useRouter} from 'vue-router';

export default defineComponent({
  name: 'AdminForm',
  props: {
    admin: {
      type: Object as PropType<AdminResponse>,
      required: false
    }
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    return {store, router};
  },
  data() {
    return {
      adminForm: this.admin || {id: ''}
    };
  },
  methods: {
    async sendForm() {
      const selfRouter = this.router;
      const token = this.store.getters.getAccessToken;
      if (this.adminForm && this.adminForm.id) {
        HttpClient.put('/v1/admins', this.adminForm, token).then((response: Response) => {
          response.json().then(() => {
            selfRouter.push('/admin/management');
          }).catch((error) => {
            console.log(error);
          });
        });
      } else {
        const response = await HttpClient.post('/v1/admins', this.adminForm, token);
        if (response.ok) {
          selfRouter.push('/admin/management');
        } else {
          console.log('HTTP ERROR: ' + response.status);
        }
      }
    }
  }
});
</script>

<style scoped lang="scss">

</style>
