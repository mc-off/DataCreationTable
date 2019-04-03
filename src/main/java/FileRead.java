import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class FileRead {
    private ArrayList<Person> personsArrayDeque;
    private static final String FIRST_NAME_PATH ="src/main/resources/firstName.txt";
    private static final String SECOND_NAME_PATH ="src/main/resources/secondName.txt";
    private static final String THIRD_NAME_PATH ="src/main/resources/middleName.txt";
    private static final String DATE_PATH ="src/main/resources/birthDate.txt";
    private static final String COUNTRY_PATH ="src/main/resources/country.txt";
    private static final String REGION_PATH ="src/main/resources/region.txt";
    private static final String CITY_PATH ="src/main/resources/city.txt";
    private static final String STREET_PATH ="src/main/resources/street.txt";
    private static final String HOUSE_PATH ="src/main/resources/house.txt";
    private static final String FLAT_PATH ="src/main/resources/flatNumber.txt";
    private int generatePersonNumber;

    FileRead() {}

    void take(int maxPersonNumber) {
        setRandomPersonNumber(maxPersonNumber);
        getNewPersonsList(generatePersonNumber);
        readPersonsData();
    }

    private void readPersonsData() {
        readPersonsNames();
        readPersonsBirthDate();
        readPersonsLocation();
    }

    void excelExport()
    {
        ExcelExport newExport = new ExcelExport();
        newExport.create(getPersonsArrayDeque());
    }

    void pdfExport()
    {
        PdfExport newExport = new PdfExport();
        newExport.create(getPersonsArrayDeque());
    }

    private void readPersonsNames() {
        readAndSetData(FIRST_NAME_PATH);
        readAndSetData(SECOND_NAME_PATH);
        readAndSetData(THIRD_NAME_PATH);
    }
    private void readPersonsBirthDate() {
        readAndSetData(DATE_PATH);
    }

    private void readPersonsLocation() {
        readAndSetData(COUNTRY_PATH);
        readAndSetData(REGION_PATH);
        readAndSetData(CITY_PATH);
        readAndSetData(STREET_PATH);
        readAndSetData(HOUSE_PATH);
        readAndSetData(FLAT_PATH);
    }

    private void readAndSetData(String filePath)
    {
        try {
            Scanner fileScanner = getScanner(filePath);
            String[] stringArray = new String[generatePersonNumber];
            for (int i=0; i<personsArrayDeque.toArray().length; i++)
            {
                stringArray[i] = fileScanner.nextLine();
            }
            switch (filePath) {
                case (FIRST_NAME_PATH): {
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setName(stringArray[i]);
                    }
                    break;
                case (SECOND_NAME_PATH):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setSecondName(stringArray[i]);
                    }
                    break;
                case (THIRD_NAME_PATH):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setMiddleName(stringArray[i]);
                     }
                     break;
                case (DATE_PATH): {
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setBirthDay(getDate(stringArray[i]));
                     } break;
                case (COUNTRY_PATH):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setCountry(stringArray[i]);
                    }
                    break;
                case (REGION_PATH):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setRegion(stringArray[i]);
                    }
                    break;
                case (STREET_PATH):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setStreet(stringArray[i]);
                }
                break;
                case (CITY_PATH):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setCity(stringArray[i]);
                     }
                     break;
                case (HOUSE_PATH):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setHouse(Integer.parseInt(stringArray[i]));
                     }
                     break;
                case (FLAT_PATH):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setFlat(Integer.parseInt(stringArray[i]));
                     }
                     break;
                default: System.out.println("Couldn't compare string " + filePath);
                      break;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setPersonNumber(int personNumber) {
        this.generatePersonNumber = personNumber;
    }


    private void setRandomPersonNumber(int maxPersonNumber) {
        RandomNumberGenerator newRand = new RandomNumberGenerator();
        setPersonNumber(newRand.generate(1, maxPersonNumber));
    }

    private Scanner getScanner(String fileName) throws FileNotFoundException {
        File newFile = getFile(fileName);
        return new Scanner(newFile);
    }

    private File getFile(String fileName) throws NoSuchElementException {
        File file;
        boolean flag;
        do {
            flag = true;
            file = new File(fileName);

            if (!file.exists()) {
                flag = false;
                System.out.println("Check file name, file doesn't exists");
            }
        } while (!flag);
        return (file);
    }
    private Date getDate(String str_date) throws java.text.ParseException{
        DateFormat formatter;
        Date date;
        formatter = new SimpleDateFormat("dd.mm.yyyy");
        date = formatter.parse(str_date);
        return date;
    }

    private void getNewPersonsList(int personNumber)
    {
        ArrayList<Person> newPersonArrayDeque = new ArrayList<>();
        for (int i=0;i<personNumber;i++)
        {
            Person newPerson = new Person();
            newPersonArrayDeque.add(newPerson);
        }
        setPersonsArrayDeque(newPersonArrayDeque);
    }

    private ArrayList<Person> getPersonsArrayDeque() {
        return personsArrayDeque;
    }

    private void setPersonsArrayDeque(ArrayList<Person> personsArrayDeque) {
        this.personsArrayDeque = personsArrayDeque;
    }



}
