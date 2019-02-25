import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



public class ExcelExport {

    private Workbook workbook;
    private Sheet sheet;
    private Font headerFont;
    private CellStyle headerCellStyle;
    private Row headerRow;
    private FileOutputStream fileOut;
    private static String[] columns = { "Имя", "Фамилия", "Отчество",
            "Возраст", "Дата рождения", "ИНН", "Индекс", "Страна", "Регион", "Город", "Улица", "Дом", "Квартира" };
    ExcelExport(){}

    public void setNewWorkbook()
    {
        Workbook workbook = new HSSFWorkbook();
        this.workbook=workbook;
    }

    public void setNewSheet(String name)
    {
        Sheet sheet = getWorkbook().createSheet(name);
        this.sheet=sheet;
    }

    public Workbook getWorkbook()
    {
        return workbook;
    }

    public void setFont()
    {
        Font headerFont = getWorkbook().createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = getWorkbook().createCellStyle();
        headerCellStyle.setFont(headerFont);
        this.headerFont = headerFont;
        this.headerCellStyle = headerCellStyle;
    }
    public void createRow()
    {
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        this.headerRow = headerRow;
    }

    public void createDataCells(ArrayList<Person> personArrayList)
    {
        int rowNum = 1;


        for (Person person:personArrayList) {
            Row row = sheet.createRow(rowNum++);
            int i=0;
            row.createCell(i).setCellValue(person.getName()); i++;
            row.createCell(i).setCellValue(person.getSecondName());i++;
            row.createCell(i).setCellValue(person.getThirdName());i++;
            row.createCell(i).setCellValue(person.getAge());i++;
            row.createCell(i).setCellValue(person.getBirthDay().toString());i++;
            row.createCell(i).setCellValue(person.getINN());i++;
            row.createCell(i).setCellValue(person.getIndex());i++;
            row.createCell(i).setCellValue(person.getCountry());i++;
            row.createCell(i).setCellValue(person.getRegion());i++;
            row.createCell(i).setCellValue(person.getCity());i++;
            row.createCell(i).setCellValue(person.getStreet());i++;
            row.createCell(i).setCellValue(person.getHouse());i++;
            row.createCell(i).setCellValue(person.getFlat());i++;
        }
    }
    public void resizeColumns()
    {
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    public void writeFileOut()
    {
        try {
            FileOutputStream fileOut = new FileOutputStream("contacts.xls");
            workbook.write(fileOut);
            fileOut.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void create(ArrayList<Person> personArrayList)
    {
            setNewWorkbook();
            setNewSheet("Persons");
            setFont();
            createRow();
            createDataCells(personArrayList);
            resizeColumns();
            writeFileOut();
    }

}