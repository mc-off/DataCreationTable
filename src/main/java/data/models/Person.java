package data.models;

import generators.PersonalINNGenerator;
import generators.RandomNumberGenerator;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class Person {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String secondName;
    @Getter
    @Setter
    private String middleName;
    @Getter
    @Setter
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

    public Person(){
        setAutoGenIndex();
        setAutoGenINN();
    }

    public String getNiceLookingDate()
    {
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd.mm.yyyy");
        return (formatter.format(getBirthDay()));
    }

    private void setAutoGenGender()
    {
        if (middleName.charAt(middleName.length()-1)=='а') {
            setGender('Ж');
        }
        else
            if (middleName.charAt(middleName.length()-1)=='ч') {
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

    private void setGender(char gender) {
        this.gender = gender;
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

    public void setBirthDay(Date birthDate) {
        this.birthDate = birthDate;
        setAge(getFullAge(birthDate));

    }

    private void setINN(String INN) {
        this.INN = INN;
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
