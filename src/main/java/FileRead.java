import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class FileRead {
    private ArrayList<Person> personsArrayDeque;
    private static final String firstNamePath="src/main/resources/firstName.txt";
    private static final String secondNamePath="src/main/resources/secondName.txt";
    private static final String thirdNamePath="src/main/resources/thirdName.txt";
    private static final String datePath="src/main/resources/birthDate.txt";
    private static final String countryPath="src/main/resources/country.txt";
    private static final String regionPath="src/main/resources/region.txt";
    private static final String cityPath="src/main/resources/city.txt";
    private static final String streetPath="src/main/resources/street.txt";
    private static final String housePath="src/main/resources/house.txt";
    private static final String flatPath="src/main/resources/flatNumber.txt";
    private static int generatePersonNumber;

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
        readAndSetData(firstNamePath);
        readAndSetData(secondNamePath);
        readAndSetData(thirdNamePath);
    }
    private void readPersonsBirthDate() {
        readAndSetData(datePath);
    }

    private void readPersonsLocation() {
        readAndSetData(countryPath);
        readAndSetData(regionPath);
        readAndSetData(cityPath);
        readAndSetData(streetPath);
        readAndSetData(housePath);
        readAndSetData(flatPath);
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
                case (firstNamePath): {
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setName(stringArray[i]);
                    }
                    break;
                case (secondNamePath):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setSecondName(stringArray[i]);
                    }
                    break;
                case (thirdNamePath):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setThirdName(stringArray[i]);
                     }
                     break;
                case (datePath): {
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setBirthDay(getDate(stringArray[i]));
                     } break;
                case (countryPath):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setCountry(stringArray[i]);
                    }
                    break;
                case (regionPath):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setRegion(stringArray[i]);
                    }
                    break;
                case (streetPath):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setStreet(stringArray[i]);
                }
                break;
                case (cityPath):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setCity(stringArray[i]);
                     }
                     break;
                case (housePath):{
                    for (int i=0; i<personsArrayDeque.toArray().length; i++)
                        personsArrayDeque.get(i).setHouse(Integer.parseInt(stringArray[i]));
                     }
                     break;
                case (flatPath):{
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
        FileRead.generatePersonNumber = personNumber;
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
