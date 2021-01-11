import Utils from '@/utils/Utils';

describe('Utils functions', () => {

    it('should return true if empty object', () => {
        const emptyObj = {};
        expect(Utils.isEmptyObject(emptyObj)).toBeTruthy()
    });

    it('should return false if not empty object', () => {
        const emptyObj = {asd: 'adas'};
        expect(Utils.isEmptyObject(emptyObj)).toBeFalsy()
    });

    it('should return true if blank string', () => {
        const blankStr = '   ';
        expect(Utils.isBlankString(blankStr)).toBeTruthy()
    });

    it('should return true if empty string', () => {
        const blankStr = '';
        expect(Utils.isBlankString(blankStr)).toBeTruthy()
    });
});
