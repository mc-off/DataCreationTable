import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class ApiDataRead {

    private RandomNumberGenerator numberGenerator;
    private JSONObject jsonObject;
    private ArrayList<Person> personArrayList;
  ApiDataRead()
  {
      this.personArrayList = new ArrayList<>();
      this.numberGenerator = new RandomNumberGenerator();
  }
  void take(int maxPersonReadNumber)
  {
     try {
         int personNumber = numberGenerator.generate(1, maxPersonReadNumber);
         for (int currentPerson = 0; currentPerson < personNumber; currentPerson++) {
             JSONObject newJsonObject = Unirest.get("http://randomuser.ru/api.json").asJson().getBody().getArray()
                     .getJSONObject(0)
                     .getJSONObject("user");
             addNewPersonToList();
             setJsonObject(newJsonObject);
             readPersonsData(currentPerson);
         }
     }
     catch (UnirestException e)
     {
         System.out.println("Internet connection trouble, stop taking data from internet");
     }
  }

  boolean connect()
  {
      try {
          Unirest.get("http://randomuser.ru/api.json").asJson();
          return true;
      }
      catch (UnirestException e)
      {
          System.out.println("Internet connection trouble, couldn't connect to API server");
          return false;
      }
  }
  void setNewRandomJSON()
  {
      try {
          JSONObject newJsonObject = Unirest.get("http://randomuser.ru/api.json").asJson().getBody().getArray()
                  .getJSONObject(0)
                  .getJSONObject("user");
          setJsonObject(newJsonObject);
      }
      catch (UnirestException e)
      {
          System.out.println("Internet connection trouble");
      }

  }

  private void addNewPersonToList()
  {
      Person newPerson = new Person();
      this.personArrayList.add(newPerson);
  }
    private void readPersonsData(int currentPerson) {
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

    private void setPersonsNames(int currentPerson) {
      setPersonsFirstName(currentPerson);
      setPersonsSecondName(currentPerson);
      setPersonsThirdName(currentPerson);
    }

    private void setPersonsBirthDate(int currentPerson) {
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
    Date getRandomBirthDate ()
    {
        try {
            JSONObject persona = Unirest.get("https://randomuser.me/api/?nat=fr").asJson()
                    .getBody().getObject().getJSONArray("results")
                    .getJSONObject(0);
            return getDate(persona.getJSONObject("dob").getString("date"));
        }
        catch (UnirestException e)
        {
            System.out.println("No internet connection, continue with default date 01.01.1970");
            return getDate("1970-01-01");
        }
    }

    Date getDate(String str_date){
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

  private void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
  }

    private ArrayList<Person> getPersonArrayList() {
        return personArrayList;
    }
    private JSONObject getJSONObject(String jsonName)
    {
        return jsonObject.getJSONObject(jsonName);
    }

    String getFirstName()
    {
        return getJSONObject("name").getString("first");
    }

    String getSecondName()
    {
        return getJSONObject("name").getString("last");
    }

    String getThirdName()
    {
        return getJSONObject("name").getString("middle");
    }

    String getDefaultCountry()
    {
        return ("Россия");
    }

    String getRegion()
    {
        return getJSONObject("location").getString("state");
    }

    String getCity()
    {
        return getJSONObject("location").getString("city");
    }

    String getStreet()
    {
        return getJSONObject("location").getString("street");
    }

    Integer getHouse()
    {
        return getJSONObject("location").getInt("building");
    }

    Integer getIndex()
    {
        return getJSONObject("location").getInt("zip");
    }

    int getFlat()
    {
        return (numberGenerator.generate(1,500));
    }
}
