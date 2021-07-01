public class Parser {

    public static Float[] parseEquity(String[] table) {
        Float firstEquityVal = Float.parseFloat(table[0].substring(68, table[0].indexOf("PLN")).replace(" ", "").replace(",", "."));
        Float secondEquityVal = Float.parseFloat(table[1].substring(68, table[1].indexOf("PLN")).replace(" ", "").replace(",", "."));
        Float equitySum = firstEquityVal + secondEquityVal;
        Float firstEquityCount = Float.parseFloat(table[0].substring(81));
        Float secondEquityCount = Float.parseFloat(table[1].substring(81));
        Float equityCount = firstEquityCount + secondEquityCount;
        return new Float[] {equitySum, equityCount};
    }

    public static Float[] parseObligations(String[] table) {
        Float firstObliVal = Float.parseFloat(table[2].substring(48, table[2].indexOf("PLN")).replace(" ", "").replace(",", "."));
        Float secondObliVal = Float.parseFloat(table[3].substring(48, table[3].indexOf("PLN")).replace(" ", "").replace(",", "."));
        Float obliSum = firstObliVal + secondObliVal;
        Float firstObliCount = Float.parseFloat(table[2].substring(61));
        Float secondObliCount = Float.parseFloat(table[3].substring(61));
        Float obliCount = firstObliCount + secondObliCount;
        return new Float[] {obliSum, obliCount};
    }

}
