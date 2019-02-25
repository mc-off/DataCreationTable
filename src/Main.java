import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args)
    {
        int maxPersonReadNumber = 11;
        FileRead newFileRead = new FileRead();

        //for (int i=0;i<INNGen.getINN().length;i++)
        //System.out.print((int)INNGen.getINN()[i]);
        newFileRead.take(maxPersonReadNumber);
        newFileRead.excelExport();
        /*Person[] personList = new Person[maxPersonReadNumber];
        Main listFiles = new Main();
        listFiles.listAllFiles("resources");
        Date birthDate = listFiles.getDate(17, 12, 1998);
        //FileRead newFile1 = new FileRead(FileName1);
        */
    }

    public void listAllFiles(String path){
        System.out.println("In listAllfiles(String path) method");
        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        readContent(filePath);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void readContent(Path filePath) throws IOException{
        System.out.println("read file " + filePath);
        List<String> fileList = Files.readAllLines(filePath);
        System.out.println("" + fileList);
    }

    public Date getDate(int day,int month, int year) {
        Calendar newCalendar = new GregorianCalendar(year, --month, day);
        Date newDate = newCalendar.getTime();
        return newDate;
    }


}
