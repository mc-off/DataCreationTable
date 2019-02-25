import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

public class PdfExport {
    private Document document;
    private PdfPTable table;
    PdfExport(){}
    public void create(ArrayList<Person> personArrayList)
    {
        createNewDocumnet();
        createTable(personArrayList.size());
        addTableHeader(table);
        addRows(table);
        addTableToDocument();
        closeDocument();

    }
    private void closeDocument()
    {
        document.close();
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
    private void createTable(int numColumns)
    {
        PdfPTable table = new PdfPTable(numColumns);
        this.table=table;
    }
    private void createNewDocumnet()
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
    private void addTableHeader(PdfPTable table) {
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
    private void addRows(PdfPTable table) {

    }

}
