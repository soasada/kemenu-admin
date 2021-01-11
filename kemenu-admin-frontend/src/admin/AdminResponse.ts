export default class AdminResponse {
    readonly id: string;
    readonly email: string;
    readonly password: string;
    readonly role: string;
    readonly createdAt: string;
    readonly updatedAt: string;

    constructor(id: string, email: string, password: string, role: string, createdAt: string, updatedAt: string) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}