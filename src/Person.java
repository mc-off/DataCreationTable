import java.util.Date;

public class Person {
    private String name;
    private String secondName;
    private String thirdName;
    private char gender;
    private int age;
    private Date birthDate;
    private String INN;
    private int index;
    private String country;
    private String region;
    private String city;
    private String street;
    private int house;
    private int flat;

    Person(){
        setAutoGenIndex();
        setAutoGenINN();
    }

    public void printPersonData()
    {
        printPersonFullName();
        printSpecialData();
        printLocation();
    }
    public void printPersonFullName()
    {
        System.out.println("Name " + name + '\n' + "Second name " + secondName + '\n' + "Third name " + thirdName + '\n');
    }
    public void printSpecialData()
    {
        System.out.println("Age " + age + '\n' + "Birth date " + birthDate + '\n' + "INN " + INN + '\n' + "Index " + index + '\n');
    }
    public void printLocation()
    {
        System.out.println("Country " + country + '\n' + "Region " + region + '\n' + "City " + city + '\n' + "House " + house + '\n');
    }
    public void setAutoGenGender()
    {
        if (thirdName.charAt(thirdName.length()-1)=='а') setGender('Ж');
        else
            setGender('М');
    }


    public void setAutoGenINN()
    {
        PersonalINNGenerator INNGen = new PersonalINNGenerator();
        setINN(INNGen.getStringINN());
    }

    public void setAutoGenIndex()
    {
        RandomNumberGenerator randomIndex = new RandomNumberGenerator();
        setIndex(randomIndex.generate(100000 ,200000));
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public char getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public Date getBirthDay() {
        return birthDate;
    }

    public String getINN() {
        return INN;
    }

    public int getIndex() {
        return index;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouse() {
        return house;
    }

    public int getFlat() {
        return flat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
        setAutoGenGender();
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBirthDay(Date birthDate) {
        this.birthDate = birthDate;
        setAge(getFullAge(birthDate));

    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public int getFullAge(Date birthDate)
    {
        Date modernDate = new Date();
        long difference = (modernDate.getTime()-birthDate.getTime())/1000; //calculate difference and convert it into sec
        long year = 60*60*24*365;
        long differenceYear = difference/year;
        return (int)differenceYear;
    }

}
