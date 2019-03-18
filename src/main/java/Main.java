import static java.lang.System.exit;

public class Main {
    public static void main(String[] args)
    {
        Main test = new Main();
        int maxPersonReadNumber = 11;
        ApiDataRead apiDataRead = new ApiDataRead();
        EditorSQL editorSQL = new EditorSQL();
        if (editorSQL.connect())
        {
            if (apiDataRead.connect())
            {

                test.goThroughApiWithSQL(maxPersonReadNumber);
            }
            else
            {
                if (editorSQL.testIfEmpty())
                {
                    test.goThroughLocalFiles(maxPersonReadNumber);
                }
                else
                {
                    test.goThrougLocalDBWithSQL();
                }
            }
        }
        else
        {
            if (apiDataRead.connect())
            {
                test.goThroughApi(maxPersonReadNumber);
            }
            else
            {
                test.goThroughLocalFiles(maxPersonReadNumber);
            }
        }
    }

    private void goThroughApiWithSQL(int maxPersonReadNumber)
    {
        EditorSQL newEditor = new EditorSQL();
        newEditor.fill(maxPersonReadNumber);
        newEditor.export();
        exit(0);
    }

    private void goThrougLocalDBWithSQL()
    {
        EditorSQL newEditor = new EditorSQL();
        newEditor.export();
        exit(0);
    }

    private void goThroughApi(int maxPersonReadNumber)
    {
        ApiDataRead newApiReader = new ApiDataRead();
        newApiReader.take(maxPersonReadNumber);
        newApiReader.excelExport();
        newApiReader.pdfExport();
        exit(0);
    }

    private void goThroughLocalFiles(int maxPersonReadNumber)
    {
        FileRead newFileRead = new FileRead();
        newFileRead.take(maxPersonReadNumber);
        newFileRead.excelExport();
        newFileRead.pdfExport();
        exit(0);
    }

}