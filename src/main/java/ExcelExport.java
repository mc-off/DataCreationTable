import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



class ExcelExport {

    private Workbook workbook;
    private Sheet sheet;
    private Font headerFont;
    private CellStyle headerCellStyle;
    private static File outFile = new File("Persons.xls");
    private  FileOutputStream fileOut;
    private static String[] columns = { "Имя", "Фамилия", "Отчество", "Пол",
            "Возраст", "Дата рождения", "ИНН", "Индекс", "Страна", "Регион", "Город", "Улица", "Дом", "Квартира" };
    ExcelExport(){}

    private void setNewWorkbook()
    {
        this.workbook=new HSSFWorkbook();
    }

    private void setNewSheet(String name)
    {
        this.sheet=getWorkbook().createSheet(name);
    }

    private Workbook getWorkbook()
    {
        return workbook;
    }

    private void setFont()
    {
        Font headerFont = getWorkbook().createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        this.headerFont = headerFont;


    }

    private void setHeaderCellStyle()
    {
        CellStyle headerCellStyle = getWorkbook().createCellStyle();
        headerCellStyle.setFont(headerFont);
        this.headerCellStyle = headerCellStyle;
    }
    private void createRow()
    {
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void createDataCells(ArrayList<Person> personArrayList)
    {
        int rowNum = 1;


        for (Person person:personArrayList) {
            Row row = sheet.createRow(rowNum++);
            int i=0;
            row.createCell(i).setCellValue(person.getName()); i++;
            row.createCell(i).setCellValue(person.getSecondName());i++;
            row.createCell(i).setCellValue(person.getMiddleName());i++;
            row.createCell(i).setCellValue(String.valueOf(person.getGender()));i++;
            row.createCell(i).setCellValue(person.getAge());i++;
            row.createCell(i).setCellValue(person.getNiceLookingDate());i++;
            row.createCell(i).setCellValue(person.getINN());i++;
            row.createCell(i).setCellValue(person.getIndex());i++;
            row.createCell(i).setCellValue(person.getCountry());i++;
            row.createCell(i).setCellValue(person.getRegion());i++;
            row.createCell(i).setCellValue(person.getCity());i++;
            row.createCell(i).setCellValue(person.getStreet());i++;
            row.createCell(i).setCellValue(person.getHouse());i++;
            row.createCell(i).setCellValue(person.getFlat());
        }
    }

    private void resizeColumns()
    {
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void writeFileOut()
    {
        try {
             fileOut = new FileOutputStream(outFile);
             workbook.write(fileOut);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                fileOut.close();
                System.out.println("File .XLS created. Path" + ' ' + outFile.getAbsolutePath());
            }
            catch (IOException e)
            {
                System.out.println("Couldn't create .XLS, close opened Persons.xls file" );
            }
        }
    }
    void create(ArrayList<Person> personArrayList)
    {
            setNewWorkbook();
            String newSheetName = ("Persons");
            setNewSheet(newSheetName);
            setFont();
            setHeaderCellStyle();
            createRow();
            createDataCells(personArrayList);
            resizeColumns();
            writeFileOut();
    }

    void createFromSQL(IteratorSQL iteratorSQL)
    {
        setNewWorkbook();
        String newSheetName = ("Persons");
        setNewSheet(newSheetName);
        setFont();
        setHeaderCellStyle();
        createRow();
        createDataCellsFromSQL(iteratorSQL);
        resizeColumns();
        writeFileOut();
    }
    private void createDataCellsFromSQL(IteratorSQL iteratorSQL)
    {
        int rowNum = 1;

        for (int j=iteratorSQL.getMinID();j<iteratorSQL.getMaxID();j++) {
            Row row = sheet.createRow(rowNum++);
            int i=0;
            try {
                row.createCell(i).setCellValue(iteratorSQL.getFirstName()); i++;
                row.createCell(i).setCellValue(iteratorSQL.getSecondName());i++;
                row.createCell(i).setCellValue(iteratorSQL.getMiddleName());i++;
                row.createCell(i).setCellValue(iteratorSQL.getGender());i++;
                row.createCell(i).setCellValue(iteratorSQL.getFullAge());i++;
                row.createCell(i).setCellValue(iteratorSQL.getNiceLookingDate());i++;
                row.createCell(i).setCellValue(String.valueOf(iteratorSQL.getInn()));i++;
                row.createCell(i).setCellValue(String.valueOf(iteratorSQL.getIndex()));i++;
                row.createCell(i).setCellValue(iteratorSQL.getCountry());i++;
                row.createCell(i).setCellValue(iteratorSQL.getRegion());i++;
                row.createCell(i).setCellValue(iteratorSQL.getCity());i++;
                row.createCell(i).setCellValue(iteratorSQL.getStreet());i++;
                row.createCell(i).setCellValue(iteratorSQL.getHouse());i++;
                row.createCell(i).setCellValue(iteratorSQL.getFlat());
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            iteratorSQL.incCurrentID();
        }
        iteratorSQL.setCurrentID(iteratorSQL.getMinID());
    }
}