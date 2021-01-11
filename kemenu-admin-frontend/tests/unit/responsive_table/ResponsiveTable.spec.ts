import {mount} from '@vue/test-utils';
import ResponsiveTable from '@/responsive_table/ResponsiveTable.vue';
import store from '@/store';
import router from '@/router';

describe('ResponsiveTable.vue', () => {
    const columnNames = ['id', 'name'];
    const tableData = [
        {id: 1, name: 'Test'},
        {id: 10, name: 'Test10'},
        {id: 11, name: 'Test11'}
    ];

    const wrapper = mount(ResponsiveTable, {
        props: {
            columnNames: columnNames,
            tableData: tableData,
            showActionsColumn: true
        } as any,
        global: {
            plugins: [store, router]
        }
    });

    it('Should render table header', () => {
        const tableHeaderCells = wrapper.findAll('th');

        expect(tableHeaderCells[0].text()).toBe('#');
        expect(tableHeaderCells[1].text()).toBe('id');
        expect(tableHeaderCells[2].text()).toBe('name');
        expect(tableHeaderCells[3].text()).toBe('actions');
    });

    it('Should render table body', () => {
        const tableBodyRows = wrapper.findAll('tbody > tr');

        const row1 = tableBodyRows[0].findAll('td');
        const row2 = tableBodyRows[1].findAll('td');
        const row3 = tableBodyRows[2].findAll('td');

        const dataRow11 = Number(row1[1].text());
        const dataRow12 = row1[2].text();
        const dataRow21 = Number(row2[1].text());
        const dataRow22 = row2[2].text();
        const dataRow31 = Number(row3[1].text());
        const dataRow32 = row3[2].text();

        expect(dataRow11).toBe(1);
        expect(dataRow12).toBe('Test');
        expect(dataRow21).toBe(10);
        expect(dataRow22).toBe('Test10');
        expect(dataRow31).toBe(11);
        expect(dataRow32).toBe('Test11');
    });

    it('Should filter table data with searched query', async () => {
        // TODO: This is a hack until vue-test-utils 2 is released
        const componentVueInstance = wrapper.vm as any;
        componentVueInstance.tableSearch('Test1');
        await componentVueInstance.$nextTick();
        const searchedValues = componentVueInstance.getRows;

        expect(searchedValues.length).toStrictEqual(2);
        expect(searchedValues[0]).toStrictEqual({id: 10, name: 'Test10'});
        expect(searchedValues[1]).toStrictEqual({id: 11, name: 'Test11'});
    });

    it('Should renders nested objects', () => {
        const wrapper = mount(ResponsiveTable, {
            props: {
                columnNames: ['id', 'address.zip'],
                tableData: [
                    {id: 1, address: {zip: 11111}},
                    {id: 2, address: {zip: 22222}}
                ],
                showActionsColumn: true
            } as any,
            global: {
                plugins: [store, router]
            }
        });

        const tableBodyRows = wrapper.findAll('tbody > tr');

        const row1 = tableBodyRows[0].findAll('td');
        const row2 = tableBodyRows[1].findAll('td');

        const dataRow11 = Number(row1[1].text());
        const dataRow12 = Number(row1[2].text());
        const dataRow21 = Number(row2[1].text());
        const dataRow22 = Number(row2[2].text());

        expect(dataRow11).toBe(1);
        expect(dataRow12).toBe(11111);
        expect(dataRow21).toBe(2);
        expect(dataRow22).toBe(22222);
    });

    it('Should not render actions column', async () => {
        const wrapper = mount(ResponsiveTable, {
            props: {
                columnNames: ['id', 'address.zip'],
                tableData: [
                    {id: 1, address: {zip: 11111}},
                    {id: 2, address: {zip: 22222}}
                ],
                showActionsColumn: false
            } as any,
            global: {
                plugins: [store, router]
            }
        });

        const tableHeaderCells = wrapper.findAll('th');

        expect(tableHeaderCells[0].text()).toBe('#');
        expect(tableHeaderCells[1].text()).toBe('id');
        expect(tableHeaderCells[2].text()).toBe('address.zip');
        expect(tableHeaderCells[3]).toBe(undefined);
    });
});