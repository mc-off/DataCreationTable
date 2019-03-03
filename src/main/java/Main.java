public class Main {
    public static void main(String[] args)
    {
        int maxPersonReadNumber = 11;
        FileRead newFileRead = new FileRead();
        newFileRead.take(maxPersonReadNumber);
        newFileRead.excelExport();
        newFileRead.pdfExport();
    }

}