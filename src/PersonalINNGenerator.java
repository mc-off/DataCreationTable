import java.util.ArrayList;

public class PersonalINNGenerator {
    private ArrayList INN;
    private String stringINN;
    private int region;
    private int[] officeArray;
    private RandomNumberGenerator randomGen;
    PersonalINNGenerator()
    {
        setNewINN();
        randomGen = new RandomNumberGenerator();

        int newRegion = 77;
        setRegion(newRegion);

        setOfficeArrayOfReg77();

        generate();
        setStringINN();
    }

    public void setStringINN()
    {
        this.stringINN = INN.toString();
        for (int i=0;i<stringINN.length();i++)
        {
            if ((stringINN.charAt(i)<'0')||(stringINN.charAt(i)>'9'))
            {
                StringBuilder stringBuilder = new StringBuilder(stringINN);
                stringBuilder.deleteCharAt(i);
                this.stringINN = stringBuilder.toString();
                i--;
            }
        }
    }
    public String getStringINN()
    {
        return stringINN;
    }

    private void setNewINN()
    {
        ArrayList newArray = new ArrayList();
        for (int i=0;i<12;i++)
        {
            newArray.add(0);
        }
        this.INN = newArray;
    }

    public ArrayList getINN()
    {
    return INN;
    }


    private void setOfficeArrayOfReg77()
    {
        int[] newOfficeArray = new int[40];

        for (int i=0;i<36;i++)
        {
            newOfficeArray[i]=i+1;
        }
        newOfficeArray[36]=43;
        newOfficeArray[37]=46;
        newOfficeArray[38]=47;
        newOfficeArray[39]=53;

        setOfficeArray(newOfficeArray);
    }
    private void setOfficeArray(int[] newOfficeArray)
    {
        this.officeArray = newOfficeArray;
    }

    private void setRegion(int newRegion)
    {
        this.region=newRegion;
    }


    public void generate()
    {
        setINNRegion();
        setINNOffice();
        setINNMiddlePath();
        setINNFirstValidNum();
        setINNSecondValidNum();
    }

    public void setINNRegion()
    {
        INN.set(0,region / 10);
        INN.set(1,region % 10);
    }

    public void setINNOffice()
    {
        int genOffice = generateOffice();
        INN.set(2,genOffice / 10);
        INN.set(3,genOffice % 10);
    }

    public void setINNMiddlePath()
    {
        for (int i=4;i<10;i++)
        {
            INN.set(i,randomGen.generate(0,9));
        }
    }
    public int getINNnum (int index)
    {
        return (int)INN.get(index);
    }

    public int generateOffice()
    {
        return officeArray[randomGen.generate(0,officeArray.length-1)];
    }

    public void setINNFirstValidNum()
    {
        int firstValidNum = (((getINNnum(0))*7+(getINNnum(1))*2+(getINNnum(2))*4+(getINNnum(3))*10+(getINNnum(4))*3 +(getINNnum(5))*5+(getINNnum(6))*9+(getINNnum(7))*4+(getINNnum(8))*6+(getINNnum(9))*8)%11)%10;
        INN.set(10,firstValidNum);

    }
    public void setINNSecondValidNum()
    {
        int secondValidNum = (((getINNnum(0))*3+(getINNnum(1))*7+(getINNnum(2))*2+(getINNnum(3))*4+(getINNnum(4))*10 +(getINNnum(5))*3+(getINNnum(6))*5+(getINNnum(7))*9+(getINNnum(8))*4+(getINNnum(9))*6 + +(getINNnum(10))*8)%11)%10;

        INN.set(11,secondValidNum);
    }
}
