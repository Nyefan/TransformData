import data.Sheet;

public class MainClass {
  public static void main(String... args) {

    var sheet = new Sheet();
    sheet.setCell("A1", "5");
    sheet.setCell("B1", "6");
    sheet.setCell("B2", "=A1+B1");

    System.out.println(sheet.getCell("A1"));
    System.out.println(sheet.getCell("B2"));

    sheet.setCell("A1", "4");

    System.out.println(sheet.getCell("A1"));
    System.out.println(sheet.getCell("B2"));

    sheet.setCell("B3", "=A1+B2");

    System.out.println(sheet.getCell("A1"));
    System.out.println(sheet.getCell("B2"));
    System.out.println(sheet.getCell("B3"));
  }
}
