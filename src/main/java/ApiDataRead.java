import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import models.*;
import org.json.JSONArray;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class ApiDataRead {

    private RandomNumberGenerator numberGenerator;

    private static String baseUrl = "http://randomuser.ru/api.json";
    @Getter
    @Setter
    private ArrayList<Person> personArrayList;
    @Getter
    @Setter
    private User randomUser;

    ApiDataRead()
  {
      this.personArrayList = new ArrayList<>();
      this.numberGenerator = new RandomNumberGenerator();
  }
  void take(int maxPersonReadNumber)
  {
         int personNumber = numberGenerator.generate(1, maxPersonReadNumber);
             for (int currentPerson = 0; currentPerson < personNumber; currentPerson++)
             {
                 addNewPersonToList();
                 if (setNewRandomUser())
                     readPersonsData(currentPerson);
                 else
                     break;
             }
  }


  boolean setNewRandomUser()
  {
      try {
          JSONArray newJsonObject = Unirest.get(baseUrl).asJson().getBody().getArray();
          ObjectMapper mapper = new ObjectMapper();
          List<User> list = mapper.readValue(newJsonObject.toString(), new TypeReference<ArrayList<User>>() {});
          User user = new User();
          user.setUser(list.get(0).getUser());
          setRandomUser(user);
          return true;
      }
      catch (Exception e)
      {
          System.out.println("Internet connection trouble, stop taking data from internet");
          return false;
      }
  }

  boolean connect()
  {
      try {
          Unirest.get(baseUrl).asJson();
          return true;
      }
      catch (UnirestException e)
      {
          System.out.println("Internet connection trouble, couldn't connect to API server");
          return false;
      }
  }

  private void addNewPersonToList()
  {
      Person newPerson = new Person();
      this.personArrayList.add(newPerson);
  }
    private void readPersonsData(int currentPerson)
    {
        setPersonsNames(currentPerson);
        setPersonsBirthDate(currentPerson);
        setPersonsLocation(currentPerson);
    }

    void excelExport()
    {
        ExcelExport newExport = new ExcelExport();
        newExport.create(getPersonArrayList());
    }

    void pdfExport()
    {
        PdfExport newExport = new PdfExport();
        newExport.create(getPersonArrayList());
    }

    private void setPersonsNames(int currentPerson)
    {
      setPersonsFirstName(currentPerson);
      setPersonsSecondName(currentPerson);
      setPersonsThirdName(currentPerson);
    }

    private void setPersonsBirthDate(int currentPerson)
    {
        personArrayList.get(currentPerson).setBirthDay(getRandomBirthDate());
    }

    private void setPersonsFirstName(int currentPerson)
    {
      personArrayList.get(currentPerson).setName(getFirstName());
    }

    private void setPersonsSecondName(int currentPerson)
    {
        personArrayList.get(currentPerson).setSecondName(getSecondName());
    }

    private void setPersonsThirdName(int currentPerson)
    {
        personArrayList.get(currentPerson).setThirdName(getThirdName());
    }

    private void setPersonsLocation(int currentPerson) {
        setDefaultPersonsCountry(currentPerson);
        setPersonsRegion(currentPerson);
        setPersonsCity(currentPerson);
        setPersonsStreet(currentPerson);
        setPersonsHouse(currentPerson);
        setPersonsRandomFlatNumber(currentPerson);
    }

    private void setDefaultPersonsCountry(int currentPerson)
    {
        personArrayList.get(currentPerson).setCountry(getDefaultCountry());
    }

    private void setPersonsRegion(int currentPerson)
    {
        personArrayList.get(currentPerson).setRegion(getRegion());
    }
    private void setPersonsCity(int currentPerson)
    {
        personArrayList.get(currentPerson).setCity(getCity());
    }

    private void setPersonsStreet(int currentPerson)
    {
        personArrayList.get(currentPerson).setStreet(getStreet());
    }

    private void setPersonsHouse(int currentPerson)
    {
        personArrayList.get(currentPerson).setHouse(getHouse());
    }

    private void setPersonsRandomFlatNumber(int currentPerson)
    {
        personArrayList.get(currentPerson).setFlat(getFlat());
    }

    Date getRandomBirthDate() {
        String baseUrlForBirthDate = "https://randomuser.me/api/?nat=fr";
        Response response = RestAssured.given()
                .baseUri(baseUrlForBirthDate)
                .get();
        BirthDate birthDate = response.as(BirthDate.class);
        return getDate(birthDate.getResults().get(0).getDob().getDate());
    }

    private Date getDate(String str_date){
        DateFormat formatter;
        Date date = new Date();
        try {
            formatter = new SimpleDateFormat("yyyy-mm-dd");
            date = formatter.parse(str_date);
        }
        catch (java.text.ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    String getFirstName()
    {
        return randomUser.getUser().getName().getFirst();
    }

    String getSecondName()
    {
        return randomUser.getUser().getName().getLast();
    }

    String getThirdName()
    {
        return randomUser.getUser().getName().getMiddle();
    }

    String getDefaultCountry()
    {
        return ("Россия");
    }

    String getRegion()
    {
        return randomUser.getUser().getLocation().getState();
    }

    String getCity()
    {
        return randomUser.getUser().getLocation().getCity();
    }

    String getStreet()
    {
        return randomUser.getUser().getLocation().getStreet();
    }

    Integer getHouse()
    {
        return randomUser.getUser().getLocation().getBuilding();
    }

    Integer getIndex()
    {
        return randomUser.getUser().getLocation().getZip();
    }

    int getFlat()
    {
        return (numberGenerator.generate(1,500));
    }
}
