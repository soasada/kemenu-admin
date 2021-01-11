export default class Utils {

    private constructor() {
        // Utility class, does not make sense to have an instance
    }

    public static isEmptyObject<T extends object>(obj: T): boolean {
        return Object.keys(obj).length === 0 && obj.constructor === Object;
    }

    public static isBlankString(str: string): boolean {
        return (!str || /^\s*$/.test(str));
    }
}