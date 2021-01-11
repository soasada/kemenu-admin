export default class JWT {
    readonly exp: number;
    readonly roles: Array<string>;
    readonly sub: string;

    constructor(exp: number, roles: Array<string>, sub: string) {
        this.exp = exp;
        this.roles = roles;
        this.sub = sub;
    }
}