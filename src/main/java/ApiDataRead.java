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
    private int currentPerson;
  ApiDataRead(){
      this.personArrayList = new ArrayList<>();
  }
  void take(int maxPersonReadNumber)
  {
     this.numberGenerator = new RandomNumberGenerator();
     try {
         int personNumber = numberGenerator.generate(1, maxPersonReadNumber);
         for (currentPerson = 0; currentPerson < personNumber; currentPerson++) {
             JSONObject newJsonObject = Unirest.get("http://randomuser.ru/api.json").asJson().getBody().getArray()
                     .getJSONObject(0)
                     .getJSONObject("user");
             addNewPersonToList();
             setJsonObject(newJsonObject);
             readPersonsData();
         }
     }
     catch (UnirestException e)
     {
         System.out.println("Internet connection trouble, stop taking data from internet");
     }
  }

  private void addNewPersonToList()
  {
      Person newPerson = new Person();
      this.personArrayList.add(newPerson);
  }
    private void readPersonsData() {
        setPersonsNames();
        setPersonsBirthDate();
        setPersonsLocation();
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

    private void setPersonsNames() {
      JSONObject nameJsonObject = jsonObject.getJSONObject("name");
      setPersonsFirstName(nameJsonObject);
      setPersonsSecondName(nameJsonObject);
      setPersonsThirdName(nameJsonObject);
    }

    private void setPersonsBirthDate() {
        personArrayList.get(currentPerson).setBirthDay(getRandomBirthDate());
    }

    private void setPersonsFirstName(JSONObject nameJson)
    {
      personArrayList.get(currentPerson).setName(nameJson.getString("first"));
    }

    private void setPersonsSecondName(JSONObject nameJson)
    {
        personArrayList.get(currentPerson).setSecondName(nameJson.getString("last"));
    }

    private void setPersonsThirdName(JSONObject nameJson)
    {
        personArrayList.get(currentPerson).setThirdName(nameJson.getString("middle"));
    }

    private void setPersonsLocation() {
      JSONObject locationJson = jsonObject.getJSONObject("location");
        setDefaultPersonsCountry();
        setPersonsRegion(locationJson);
        setPersonsCity(locationJson);
        setPersonsStreet(locationJson);
        setPersonsHouse(locationJson);
        setPersonsRandomFlatNumber(numberGenerator.generate(1,500));
    }

    private void setDefaultPersonsCountry()
    {
        personArrayList.get(currentPerson).setCountry("Россия");
    }

    private void setPersonsRegion(JSONObject locationJson)
    {
        personArrayList.get(currentPerson).setRegion(locationJson.getString("state"));
    }
    private void setPersonsCity(JSONObject locationJson)
    {
        personArrayList.get(currentPerson).setCity(locationJson.getString("city"));
    }

    private void setPersonsStreet(JSONObject locationJson)
    {
        personArrayList.get(currentPerson).setStreet(locationJson.getString("street"));
    }

    private void setPersonsHouse(JSONObject locationJson)
    {
        personArrayList.get(currentPerson).setHouse(locationJson.getInt("building"));
    }

    private void setPersonsRandomFlatNumber(int randomFlat)
    {
        personArrayList.get(currentPerson).setFlat(randomFlat);
    }
    private Date getRandomBirthDate ()
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

  private void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
  }

    private ArrayList<Person> getPersonArrayList() {
        return personArrayList;
    }
}
