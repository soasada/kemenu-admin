import CustomerResponse from '@/customers/CustomerResponse';

export default interface CustomersState {
    customers: CustomerResponse[];
    loadingCustomers: boolean;
}