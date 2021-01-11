<template>
  <div class="responsive-table">
    <div class="responsive-table-loading" v-if="loadingData">
      <div class="text-center spinner-border" style="width: 3rem; height: 3rem;" role="status"></div>
    </div>

    <div class="row mb-2">
      <div class="col-1 text-left">
        <select class="custom-select custom-select-sm d-block w-100" @change="changeElementsPerPage($event.target.value)">
          <option value="10">10</option>
          <option value="25">25</option>
          <option value="50">50</option>
          <option value="100">100</option>
        </select>
      </div>

      <div class="col-11 text-right">
        <input type="text" placeholder="Search" @keydown.enter="tableSearch($event.target.value)">
      </div>
    </div>

    <div class="table-responsive">
      <table class="table table-hover table-bordered">
        <thead>
        <tr>
          <th>#</th>
          <th v-for="columnName in columnNames" :key="columnName">
            {{ columnName }}
          </th>
          <th v-if="showActionsColumn">actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(row, index) in getRows" :key="row.id">
          <td>{{ index }}</td>
          <td v-for="columnName in columnNames" :key="columnName">
            {{ discoverValue(row, columnName) }}
          </td>
          <td v-if="showActionsColumn">
            <a href="#" @click.prevent="$emit('edit', row)">Edit</a>&nbsp;|
            <a href="#" @click.prevent="$emit('delete', row)">Delete</a>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="row">
      <div class="col-2 text-left">
        <p>Showing {{ getRows.length }} of {{ tableData.length }}</p>
      </div>
      <div class="col-10 text-right">
        <div class="number"
             v-for="i in numPages"
             :key="i"
             :class="i === currentPage ? 'active' : ''"
             @click="changePage(i)">{{ i }}
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {computed, defineComponent, PropType, ref} from 'vue';

export default defineComponent({
  emits: ['edit', 'delete'],
  name: 'ResponsiveTable',
  props: {
    columnNames: {
      type: Array as () => string[],
      required: true
    },
    tableData: {
      type: Array as PropType<Record<string, unknown>[]>,
      required: true,
    },
    showActionsColumn: {
      type: Boolean,
      required: true
    },
    loadingData: {
      type: Boolean,
      required: true
    }
  },
  setup(props) {
    const currentPage = ref(1);
    const elementsPerPage = ref(10);
    const rows = ref<Record<string, unknown>[]>([]);

    const getRows = computed(() => {
      const start = (currentPage.value - 1) * elementsPerPage.value;
      const end = Number(start) + Number(elementsPerPage.value); // TODO: Me concatena los dos números si no hago la conversión explícita, HIPER LOCO ME QUEDO CHARLY

      if (rows.value.length > 0) {
        return rows.value.slice(start, end);
      } else {
        return props.tableData.slice(start, end);
      }
    });

    const tableSearch = (searchQuery: string) => {
      if (searchQuery) {
        rows.value = props.tableData.filter((row) => {
          return JSON.stringify(row).toUpperCase().indexOf(searchQuery.toUpperCase()) > -1;
        });
      } else {
        rows.value = props.tableData;
      }
    };

    const changePage = (page: number) => {
      currentPage.value = page;
    };

    const discoverValue = (row: Record<string, unknown>, columnName: string): unknown => {
      if (row) {
        const properties = columnName.split('.');

        if (properties.length === 1) {
          const leafValue = row[properties[0]];
          if (leafValue) {
            return leafValue;
          } else {
            return '';
          }
        } else {
          return discoverValue(row[properties[0]] as Record<string, unknown>, properties.slice(1).join('.'));
        }
      } else {
        return '';
      }
    };

    const numPages = computed(() => {
      if (rows.value.length > 0) {
        return Math.ceil(rows.value.length / elementsPerPage.value);
      } else {
        return Math.ceil(props.tableData.length / elementsPerPage.value);
      }
    });

    const changeElementsPerPage = (elements: number) => {
      changePage(1);
      elementsPerPage.value = elements;
    };

    return {
      currentPage,
      getRows,
      tableSearch,
      changePage,
      discoverValue,
      numPages,
      changeElementsPerPage
    };
  }
});
</script>

<style scoped lang="scss">
.number {
  display: inline-block;
  padding: 4px 10px;
  color: #FFF;
  border-radius: 4px;
  background: #2c3e50;
  margin: 0 5px;
  cursor: pointer;
}

.number:hover, .number.active {
  background: #42b983 !important;
  border: #42b983 !important;
}
</style>
