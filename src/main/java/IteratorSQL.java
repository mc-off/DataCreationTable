import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IteratorSQL {
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private int currentID;
    private int maxID;
    private int minID;

    IteratorSQL(String url, String user, String password)
    {
        try {
            con = DriverManager.getConnection(url, user, password);
            // disable autocommit
            con.setAutoCommit(false);
            // getting Statement object to execute query
            stmt = con.createStatement();
            this.minID = getPrivateMinID();
            this.maxID=getPrivateMaxID();
            this.currentID = minID;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    void close()
    {
        try {
            con.close();
        } catch (SQLException se) { /*can't do anything */ }
        try {
            stmt.close();
        } catch (SQLException se) { /*can't do anything */ }
        try {
            rs.close();
        } catch (SQLException se) { /*can't do anything */ }
    }

    String getFirstName()
    {
        try {
            String responce = "Select persons.name from persons where (address_id=?)";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            String dataString = "";
            while (rs.next())
                dataString = rs.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }
    String getSecondName()
    {
        try {
            String responce = "Select persons.surname from persons where (address_id=?)";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            String dataString = "";
            while (rs.next())
                dataString = rs.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    String getThirdName()
    {
        try {
            String responce = "Select middlename from persons where address_id=?";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            String dataString = "";
            while (rs.next())
                dataString = rs.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    private Date getBirthDate()
    {
        try {
            String responce = "Select birthday from persons where address_id=?";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            Date selectedDate= new Date();
            while (rs.next())
            selectedDate = rs.getDate(1);
            return selectedDate;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return new Date();
        }
    }

    String getGender()
    {
        try {
            String responce = "Select gender from persons where address_id=?";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            String dataString = "";
            while (rs.next())
                dataString = rs.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    String getInn()
    {
        try {
            String responce = "Select inn from persons where address_id=?";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            String dataString = "";
            while (rs.next())
                dataString = rs.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    int getIndex()
    {
        try {
            String responce = "Select postcode from address where id=?";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            int num=0;
            while (rs.next())
                num = rs.getInt(1);
            return num;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    String getCountry()
    {
        try {
            String responce = "Select country from address where id=?";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            String dataString = "";
            while (rs.next())
                dataString = rs.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    String getRegion()
    {
        try {
            String responce = "Select region from address where id=?";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            String dataString = "";
            while (rs.next())
                dataString = rs.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }
    String getCity()
    {
        try {
            String responce = "Select city from address where id=?";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            String dataString = "";
            while (rs.next())
                dataString = rs.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    String getStreet()
    {
        try {
            String responce = "Select street from address where id=?";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            String dataString="";
            while (rs.next())
                dataString = rs.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }
    int getHouse()
    {
        try {
            String responce = "Select house from address where id=?";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            int num=0;
            while (rs.next())
                num = rs.getInt(1);
            return num;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    int getFlat()
    {
        try {
            String responce = "Select flat from address where id=?";
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setInt (1, currentID);
            rs = ps.executeQuery();
            int num=0;
            while (rs.next())
                num = rs.getInt(1);
            return num;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    private int getPrivateMinID()
    {
        try {
            String response = "Select MIN(address_id) from persons";
            PreparedStatement ps = con.prepareStatement(response);
            rs = ps.executeQuery();
            int num=0;
            while (rs.next())
                num = rs.getInt(1);
            return num;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    private int getPrivateMaxID()
    {
        try {
            String response = "Select MAX(address_id) from persons";
            PreparedStatement ps = con.prepareStatement(response);
            rs = ps.executeQuery();
            int num=0;
            while (rs.next())
                num = rs.getInt(1);
            return num;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    int getMinID()
    {
        return minID;
    }

    int getMaxID()
    {
        return maxID;
    }

    void setCurrentID(int newID)
    {
        this.currentID=newID;
    }

    void incCurrentID()
    {
        this.currentID = currentID+1;

    }
    int getFullAge()
    {
        Date modernDate = new Date();
        long difference = (modernDate.getTime()-getBirthDate().getTime())/1000; //calculate difference and convert it into sec
        long year = 60*60*24*365;
        long differenceYear = difference/year;
        return (int)differenceYear;
    }
    String getNiceLookingDate()
    {
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return (formatter.format(getBirthDate()));
    }
}
