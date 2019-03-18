import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class Person {
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

    String getNiceLookingDate()
    {
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd.mm.yyyy");
        return (formatter.format(getBirthDay()));
    }

    private void setAutoGenGender()
    {
        if (thirdName.charAt(thirdName.length()-1)=='а') {
            setGender('Ж');
        }
        else
            if (thirdName.charAt(thirdName.length()-1)=='ч') {
                setGender('М');
            }
            else setGender('?');
    }


    private void setAutoGenINN()
    {
        PersonalINNGenerator INNGen = new PersonalINNGenerator();
        setINN(INNGen.getStringINN());
    }

    private void setAutoGenIndex()
    {
        RandomNumberGenerator randomIndex = new RandomNumberGenerator();
        setIndex(randomIndex.generate(100000 ,200000));
    }

    String getName() {
        return name;
    }

    String getSecondName() {
        return secondName;
    }

    void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    char getGender() {
        return gender;
    }

    String getThirdName() {
        return thirdName;
    }

    void setThirdName(String thirdName) {
        this.thirdName = thirdName;
        setAutoGenGender();
    }

    int getAge() {
        return age;
    }

    private void setGender(char gender) {
        this.gender = gender;
    }

    int getIndex() {
        return index;
    }

    private void setIndex(int index) {
        this.index = index;
    }

    private void setAge(int age) {
        this.age = age;
    }

    private Date getBirthDay() {
        return birthDate;
    }

    void setBirthDay(Date birthDate) {
        this.birthDate = birthDate;
        setAge(getFullAge(birthDate));

    }

    String getINN() {
        return INN;
    }

    private void setINN(String INN) {
        this.INN = INN;
    }

    String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }

    String getStreet() {
        return street;
    }

    void setStreet(String street) {
        this.street = street;
    }

    int getHouse() {
        return house;
    }

    void setHouse(int house) {
        this.house = house;
    }

    int getFlat() {
        return flat;
    }

    void setFlat(int flat) {
        this.flat = flat;
    }

    void setName(String name) {
        this.name = name;
    }

    String getCountry() {
        return country;
    }

    String getRegion() {
        return region;
    }

    void setCountry(String country) {
        this.country = country;
    }

    void setRegion(String region) {
        this.region = region;
    }

    private int getFullAge(Date birthDate)
    {
        Date modernDate = new Date();
        long difference = (modernDate.getTime()-birthDate.getTime())/1000; //calculate difference and convert it into sec
        long year = 60*60*24*365;
        long differenceYear = difference/year;
        return (int)differenceYear;
    }

}
