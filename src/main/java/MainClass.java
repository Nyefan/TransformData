import data.Sheet;

public class MainClass {
  public static void main(String... args) {

    var sheet = new Sheet();
    sheet.setCell("A1", "5");
    sheet.setCell("B1", "6");

    System.out.println(sheet.getCell("A1"));
    System.out.println(sheet.getCell("B1"));

    sheet.setCell("B2", "=A1+B1");

    System.out.println(sheet.getCell("B2"));

    sheet.setCell("A1", "4");

    System.out.println(sheet.getCell("A1"));
    System.out.println(sheet.getCell("B2"));

    sheet.setCell("B3", "=A1+B2");

    System.out.println(sheet.getCell("B3"));

    sheet.setCell("B4", "=1+2+3");
    sheet.setCell("B5", "=10-4-1");
    sheet.setCell("B6", "=1+3*5/6-4");
    sheet.setCell("B7", "=A1+B1*B2/B3-B4");

    System.out.println(sheet.getCell("B4"));
    System.out.println(sheet.getCell("B5"));
    System.out.println(sheet.getCell("B6"));
    System.out.println(sheet.getCell("B7"));

    System.out.println("all test cases passed...");
  }
}
