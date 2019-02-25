import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class FileRead {
    private ArrayList<Person> personsArrayDeque;
    private int personNumber;

    public FileRead() {
    }

    public void take(int maxPersonNumber) {
        setRandomPersonNumber(maxPersonNumber);
        getNewPersonsList(personNumber);
        readPersonsData();
    }

    private void readPersonsData() {
        readPersonsNames();
        readPersonsBirthDate();
        readPersonsLocation();
    }

    public void excelExport()
    {
        ExcelExport newExport = new ExcelExport();
        newExport.create(getPersonsArrayDeque());
    }

    private void readPersonsNames() {
        readPersonsFirstName();
        readPersonsSecondName();
        readPersonsThirdName();
    }

    private void readPersonsBirthDate() {
        try {
            String filePath = "resources/birthdate.txt";
            Scanner fileScanner = getScanner(filePath);
            for (int i=0; i<personsArrayDeque.toArray().length; i++)
            {
                String readString = fileScanner.nextLine();
                Date readDate = getDate(readString);
                personsArrayDeque.get(i).setBirthDay(readDate);
            }
                }


        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void readPersonsFirstName() {

        try {
            String filePath = "resources/firstname.txt";
            Scanner fileScanner = getScanner(filePath);
            for (int i=0; i<personsArrayDeque.toArray().length; i++)
            {
                String readString = fileScanner.nextLine();
                personsArrayDeque.get(i).setName(readString);
            }
        }


        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void readPersonsSecondName() {
        try {
            String filePath = "resources/secondname.txt";
            Scanner fileScanner = getScanner(filePath);
            for (int i=0; i<personsArrayDeque.toArray().length; i++)
            {
                String readString = fileScanner.nextLine();
                personsArrayDeque.get(i).setSecondName(readString);
            }
        }


        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void readPersonsThirdName() {
        try {
            String filePath = "resources/thirdname.txt";
            Scanner fileScanner = getScanner(filePath);
            for (int i=0; i<personsArrayDeque.toArray().length; i++)
            {
                String readString = fileScanner.nextLine();
                personsArrayDeque.get(i).setThirdName(readString);
            }
        }


        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void readPersonsLocation() {
        readPersonsCountry();
        readPersonsRegion();
        readPersonsCity();
        readPersonsStreet();
        readPersonsHouse();
        readPersonsFlatNumber();
    }

    private void readPersonsCountry() {
        try {
            String filePath = "resources/country.txt";
            Scanner fileScanner = getScanner(filePath);
            for (int i=0; i<personsArrayDeque.toArray().length; i++)
            {
                String readString = fileScanner.nextLine();
                personsArrayDeque.get(i).setCountry(readString);
            }
        }


        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void readPersonsRegion() {
        try {
            String filePath = "resources/region.txt";
            Scanner fileScanner = getScanner(filePath);
            for (int i=0; i<personsArrayDeque.toArray().length; i++)
            {
                String readString = fileScanner.nextLine();
                personsArrayDeque.get(i).setRegion(readString);
            }
        }


        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void readPersonsCity() {
        try {
            String filePath = "resources/city.txt";
            Scanner fileScanner = getScanner(filePath);
            for (int i=0; i<personsArrayDeque.toArray().length; i++)
            {
                String readString = fileScanner.nextLine();
                personsArrayDeque.get(i).setCity(readString);
            }
        }


        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void readPersonsStreet() {
        try {
            String filePath = "resources/street.txt";
            Scanner fileScanner = getScanner(filePath);
            for (int i=0; i<personsArrayDeque.toArray().length; i++)
            {
                String readString = fileScanner.nextLine();
                personsArrayDeque.get(i).setStreet(readString);
            }
        }


        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void readPersonsHouse() {
        try {
        String filePath = "resources/house.txt";
        Scanner fileScanner = getScanner(filePath);
        for (int i=0; i<personsArrayDeque.toArray().length; i++)
        {
            int readNumber = fileScanner.nextInt();
            personsArrayDeque.get(i).setHouse(readNumber);
        }
    }


        catch (Exception e)
    {
        e.printStackTrace();
    }
}

    private void readPersonsFlatNumber() {
        try {
            String filePath = "resources/flatnumber.txt";
            Scanner fileScanner = getScanner(filePath);
            for (int i=0; i<personsArrayDeque.toArray().length; i++)
            {
                int readNumber = fileScanner.nextInt();
                personsArrayDeque.get(i).setFlat(readNumber);
            }
        }


        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }


    private void setRandomPersonNumber(int maxPersonNumber) {
        RandomNumberGenerator newRand = new RandomNumberGenerator();
        setPersonNumber(newRand.generate(1, maxPersonNumber));
    }

    private Scanner getScanner(String fileName) throws FileNotFoundException {
        File newFile = getFile(fileName);
        Scanner fileScanner = new Scanner(newFile);
        return fileScanner;
    }

    private File getFile(String fileName) throws NoSuchElementException {
        File file = null;
        boolean flag = true;
        do {
            flag = true;
            file = new File(fileName);

            if (file.exists() == false) {
                flag = false;
                System.out.println("Check file name, file doesn't exists");
            }
        } while (flag == false);
        return (file);
    }
    public Date getDate(String str_date) throws java.text.ParseException{
        DateFormat formatter;
        Date date;
        formatter = new SimpleDateFormat("dd.mm.yyyy");
        date = formatter.parse(str_date);
        return date;
    }

    public void getNewPersonsList(int personNumber)
    {
        ArrayList<Person> newPersonArrayDeque = new ArrayList<>();
        for (int i=0;i<personNumber;i++)
        {
            Person newPerson = new Person();
            newPersonArrayDeque.add(newPerson);
        }
        setPersonsArrayDeque(newPersonArrayDeque);
    }

    public ArrayList<Person> getPersonsArrayDeque() {
        return personsArrayDeque;
    }

    public void setPersonsArrayDeque(ArrayList<Person> personsArrayDeque) {
        this.personsArrayDeque = personsArrayDeque;
    }



}
