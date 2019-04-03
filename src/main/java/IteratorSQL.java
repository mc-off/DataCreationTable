import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class IteratorSQL {
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

    String getFirstName() throws SQLException
    {
        try {
            String responce = "SELECT name FROM persons WHERE (address_id=?)";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setInt (1, currentID);
            resultSet = ps.executeQuery();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
    String getSecondName() throws SQLException
    {
        try {
            String responce = "Select persons.surname from persons where (address_id=?)";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setInt (1, currentID);
            resultSet = ps.executeQuery();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    String getMiddleName() throws SQLException
    {
        try {
            String responce = "Select middlename from persons where address_id=?";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setInt (1, currentID);
            resultSet = ps.executeQuery();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString(1);
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
            String responce = "Select birthday from persons where address_id=?";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setInt (1, currentID);
            resultSet = ps.executeQuery();
            Date selectedDate= new Date();
            while (resultSet.next())
            selectedDate = resultSet.getDate(1);
            return selectedDate;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    String getGender() throws SQLException
    {
        try {
            String responce = "Select gender from persons where address_id=?";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setInt (1, currentID);
            resultSet = ps.executeQuery();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    String getInn() throws SQLException
    {
        try {
            String response = "Select inn from persons where address_id=?";
            PreparedStatement ps = connection.prepareStatement(response);
            ps.setInt (1, currentID);
            resultSet = ps.executeQuery();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    int getIndex() throws SQLException
    {
        try {
            String response = "Select postcode from address where id=?";
            PreparedStatement ps = connection.prepareStatement(response);
            ps.setInt (1, currentID);
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

    String getCountry() throws SQLException
    {
        try {
            String responce = "Select country from address where id=?";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setInt (1, currentID);
            resultSet = ps.executeQuery();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    String getRegion() throws SQLException
    {
        try {
            String responce = "Select region from address where id=?";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setInt (1, currentID);
            resultSet = ps.executeQuery();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
    String getCity() throws SQLException
    {
        try {
            String responce = "Select city from address where id=?";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setInt (1, currentID);
            resultSet = ps.executeQuery();
            String dataString = "";
            while (resultSet.next())
                dataString = resultSet.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    String getStreet()throws SQLException
    {
        try {
            String responce = "Select street from address where id=?";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setInt (1, currentID);
            resultSet = ps.executeQuery();
            String dataString="";
            while (resultSet.next())
                dataString = resultSet.getString(1);
            return dataString;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw e;
        }
    }
    int getHouse()throws SQLException
    {
        try {
            String responce = "Select house from address where id=?";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setInt (1, currentID);
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
    int getFlat()throws SQLException
    {
        try {
            String responce = "Select flat from address where id=?";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setInt (1, currentID);
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
    int getFullAge() throws SQLException
    {
        Date modernDate = new Date();
        long difference = (modernDate.getTime()-getBirthDate().getTime())/1000; //calculate difference and convert it into sec
        long year = 60*60*24*365;
        long differenceYear = difference/year;
        return (int)differenceYear;
    }
    String getNiceLookingDate() throws SQLException
    {
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return (formatter.format(getBirthDate()));
    }
}
