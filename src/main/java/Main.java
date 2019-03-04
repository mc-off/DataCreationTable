import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args)
    {
        Main test = new Main();
        int maxPersonReadNumber = 11;
        try {
            Unirest.get("http://randomuser.ru/api.json").asJson();
        }
        catch (UnirestException e)
        {
            System.out.println("Internet connection trouble, go with default method");
            test.goThroughLocalFiles(maxPersonReadNumber);
        }
        test.goThroughApi(maxPersonReadNumber);
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