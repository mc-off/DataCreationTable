import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class PdfExport {
    private Document document;
    private PdfPTable table;
    private Font font;
    private static String[] columns = { "Name", "Second name", "Third name", "Gender",
            "Age", "Birth date", "INN", "Index", "Country", "Region", "City", "Street", "House", "Flat" };
    private File pdfFile;
    PdfExport(){}

    public void create(ArrayList<Person> personArrayList)
    {
        createNewDocumnet();
        createTable(columns.length);
        addTableHeader(table);
        setFont();
        for (Person person:personArrayList)
        {
            addRows(person);
        }
        addTableToDocument();
        closeDocument();

    }
    public void setFont()
    {
        try {
            try {
                BaseFont bf = BaseFont.createFont("src/main/resources/arialk.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                this.font = new Font(bf);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private Font getFont()
    {
        return font;
    }

    private void closeDocument()
    {
        document.close();
        System.out.println("Файл .PDF создан. Путь:" + " " + pdfFile.getAbsolutePath());
    }

    private void addTableToDocument()
    {
        try {
            document.add(table);
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
    }

    private void setPdfFile(File newFile)
    {
        this.pdfFile = newFile;
    }
    public void createTable(int numColumns)
    {
        try {
            PdfPTable table = new PdfPTable(numColumns);
            table.setTotalWidth(3000);
            table.setLockedWidth(true);
            table.setWidthPercentage(100);
            this.table = table;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void createNewDocumnet()
    {
        try {
            try {
                Document document = new Document(PageSize.A0.rotate(),0,0,0,0);
                setPdfFile(new File(("Persons.pdf")));
                PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                document.open();
                this.document = document;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
    }
    private void addTableHeader(PdfPTable table) {
        Stream.of(columns)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(3);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
    private void addRows(Person person) {
            table.addCell(new Phrase(person.getName(),getFont()));
            table.addCell(new Phrase(person.getSecondName(),getFont()));
            table.addCell(new Phrase(person.getThirdName(),getFont()));
            table.addCell(new Phrase(String.valueOf(person.getGender()),getFont()));
            table.addCell(String.valueOf(person.getAge()));
            table.addCell(person.getNiceLookingDate());
            table.addCell(person.getINN());
            table.addCell((String.valueOf(person.getIndex())));
            table.addCell(new Phrase(person.getCountry(),getFont()));
            table.addCell(new Phrase(person.getRegion(),getFont()));
            table.addCell(new Phrase(person.getCity(),getFont()));
            table.addCell(new Phrase(person.getStreet(),getFont()));
            table.addCell(String.valueOf(person.getHouse()));
            table.addCell(String.valueOf(person.getFlat()));
            table.setKeepTogether(true);
    }

}