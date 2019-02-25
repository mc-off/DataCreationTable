import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class PdfExport {
    private Document document;
    private PdfPTable table;
    private Font font;
    PdfExport(){}
    public void create(ArrayList<Person> personArrayList)
    {
        createNewDocumnet();
        createTable(13);
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
                BaseFont bf = BaseFont.createFont("resources/arialk.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font font = new Font(bf);
                this.font = font;
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Font getFont()
    {
        return font;
    }
    public void closeDocument()
    {
        document.close();
    }
    public void addTableToDocument()
    {
        try {
            document.add(table);
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
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
    public void createNewDocumnet()
    {
        try {
            try {
                Document document = new Document(PageSize.A0.rotate(),0,0,0,0);
                PdfWriter.getInstance(document, new FileOutputStream("Persons.pdf"));
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
    public void addTableHeader(PdfPTable table) {
        Stream.of("Name", "Second name", "Third name",
                "Age", "Birthday", "INN", "Index", "Country", "Region", "City", "Street", "House", "Flat" )
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(3);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
    public void addRows(Person person) {

        try {
            table.addCell(new Phrase(person.getName(),getFont()));
            table.addCell(new Phrase(person.getSecondName(),getFont()));
            table.addCell(new Phrase(person.getThirdName(),getFont()));
            table.addCell(String.valueOf(person.getAge()));
            table.addCell(person.getBirthDay().toString());
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
        catch (Exception E)
        {
            E.printStackTrace();
        }
    }

}
