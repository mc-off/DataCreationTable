package data.SQL;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IteratorSQL {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private int currentID;
    private int maxID;
    private int minID;

    IteratorSQL(String url, String user, String password)
    {
        try {
            connection = DriverManager.getConnection(url, user, password);
            // disable autocommit
            connection.setAutoCommit(false);
            // getting Statement object to execute query
            statement = connection.createStatement();
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
            connection.close();
        } catch (SQLException se) { se.printStackTrace(); }
        try {
            statement.close();
        } catch (SQLException se) { se.printStackTrace(); }
        try {
            resultSet.close();
        } catch (SQLException se) { se.printStackTrace(); }
    }

    public void setNewUser()
    {
        try {
            String response = "SELECT * FROM persons JOIN address ON address.id = persons.address_id where address.id=?";
            PreparedStatement ps = connection.prepareStatement(response);
            ps.setInt (1, currentID);
            resultSet = ps.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void incCurrentID()
    {
        this.currentID = currentID+1;
    }

    public String getFirstName() throws SQLException
    {
        try {
            resultSet.beforeFirst();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString("name");
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
    public String getSecondName() throws SQLException
    {
        try {
            resultSet.beforeFirst();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString("surname");
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public String getMiddleName() throws SQLException
    {
        try {
            resultSet.beforeFirst();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString("middlename");
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    private Date getBirthDate()throws SQLException
    {
        try {
            resultSet.beforeFirst();
            Date selectedDate= new Date();
            while (resultSet.next())
            selectedDate = resultSet.getDate("birthday");
            return selectedDate;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public String getGender() throws SQLException
    {
        try {
            resultSet.beforeFirst();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString("gender");
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public String getInn() throws SQLException
    {
        try {
            resultSet.beforeFirst();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString("inn");
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public int getPostcode() throws SQLException
    {
        try {
            resultSet.beforeFirst();
            int num=0;
            while (resultSet.next())
                num = resultSet.getInt("postcode");
            return num;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public String getCountry() throws SQLException
    {
        try {
            resultSet.beforeFirst();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString("country");
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public String getRegion() throws SQLException
    {
        try {
            resultSet.beforeFirst();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString("region");
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
    public String getCity() throws SQLException
    {
        try {
            resultSet.beforeFirst();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString("city");
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public String getStreet()throws SQLException
    {
        try {
            resultSet.beforeFirst();
            String dataString="";
            while (resultSet.next())
                dataString = resultSet.getString("street");
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
    public int getHouse()throws SQLException
    {
        try {
            resultSet.beforeFirst();
            int num=0;
            while (resultSet.next()) {
                num = resultSet.getInt("house");
            }
            return num;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
    public int getFlat()throws SQLException
    {
        try {
            resultSet.beforeFirst();
            int num=0;
            while (resultSet.next())
                num = resultSet.getInt("flat");
            return num;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
    private int getPrivateMinID() throws SQLException
    {
        try {
            String response = "Select MIN(address_id) from persons";
            PreparedStatement ps = connection.prepareStatement(response);
            resultSet = ps.executeQuery();
            int num=0;
            while (resultSet.next())
                num = resultSet.getInt(1);
            return num;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
    private int getPrivateMaxID()
    {
        try {
            String response = "Select MAX(address_id) from persons";
            PreparedStatement ps = connection.prepareStatement(response);
            resultSet = ps.executeQuery();
            int num=0;
            while (resultSet.next())
                num = resultSet.getInt(1);
            return num;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int getMinID()
    {
        return minID;
    }

    public int getMaxID()
    {
        return maxID;
    }

    public void setCurrentID(int newID)
    {
        this.currentID=newID;
    }

    public int getFullAge() throws SQLException
    {
        Date modernDate = new Date();
        long difference = (modernDate.getTime()-getBirthDate().getTime())/1000; //calculate difference and convert it into sec
        long year = 60*60*24*365;
        long differenceYear = difference/year;
        return (int)differenceYear;
    }
    public String getNiceLookingDate() throws SQLException
    {
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return (formatter.format(getBirthDate()));
    }
}
