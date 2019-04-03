package data.SQL;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import data.readers.ApiDataRead;
import exporters.ExcelExport;
import exporters.PdfExport;
import generators.PersonalINNGenerator;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditorSQL {

    // JDBC URL, username and password of your MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/fintech?useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String user = "root";
    private static final String password = "1234567";

    // JDBC variables for opening and managing connection
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;


    private ApiDataRead apiDataRead;

    public EditorSQL()
    {
        apiDataRead = new ApiDataRead();
    }

    public void fill(int maxPersonNumber) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            // disable autocommit
            connection.setAutoCommit(false);
            // getting Statement object to execute query
            statement = connection.createStatement();

            for (int i = 0; i < maxPersonNumber; i++)
            {
                if (apiDataRead.setNewRandomUser())
                    insertPerson();
                else
                    break;
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            //commit, close connection ,statement and result set here
           try {
               connection.commit();
            } catch (SQLException se) { se.printStackTrace();}
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
    }

    public void export() {
        IteratorSQL iteratorSQL = new IteratorSQL(url, user, password);
        ExcelExport excelExport = new ExcelExport();
        excelExport.createFromSQL(iteratorSQL);
        PdfExport pdfExport = new PdfExport();
        pdfExport.createWithSQL(iteratorSQL);
        iteratorSQL.close();
    }

    public boolean connect()
    {
        try {
            try {
                // opening database connection to MySQL server
                connection = DriverManager.getConnection(url, user, password);
                return true;
            }
            catch (CommunicationsException e)
            {
                System.out.println("Couldn't connect to database");
                return false;
            }
        } catch (SQLException e)
        {
            System.out.println("Couldn't connect to database");
            return false;
        }

    }

    private boolean tryToUpdate()
    {
        try {
            int findingID = selectID(apiDataRead.getSecondName(),apiDataRead.getFirstName(),apiDataRead.getThirdName(),getRightFormatDate(apiDataRead.getRandomBirthDate()));
            if (findingID!=0) {
                // Default requests
                String requestUpdateAddress = "UPDATE address set postcode= ? and country= ? and region= ? and city= ? and street= ? and house= ? and flat= ?" +
                        " where id= ?";
                PreparedStatement ps = connection.prepareStatement(requestUpdateAddress);
                ps.setString(1, String.valueOf(apiDataRead.getIndex()));
                ps.setString(2, apiDataRead.getDefaultCountry());
                ps.setString(3, apiDataRead.getRegion());
                ps.setString(4, apiDataRead.getCity());
                ps.setString(5, apiDataRead.getStreet());
                ps.setInt(6, apiDataRead.getHouse());
                ps.setInt(7, apiDataRead.getFlat());
                ps.setInt(8, findingID);
                ps.executeUpdate();
                return true;
            }
            return false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    private void insertPerson()
    {
        try {
            if (!tryToUpdate()) {
                if (insertAddress()) {
                    String requestInsertPerson = "Insert INTO persons (name, surname, middlename, birthday, gender, inn,  address_id) VALUES(?,?,?,?,?,?,?)";
                    PreparedStatement ps = connection.prepareStatement(requestInsertPerson);
                    ps.setString(1, apiDataRead.getFirstName());
                    ps.setString(2, apiDataRead.getSecondName());
                    ps.setString(3, apiDataRead.getThirdName());
                    ps.setString(4, getRightFormatDate(apiDataRead.getRandomBirthDate()));
                    ps.setString(5, String.valueOf(getGender()));
                    ps.setString(6, getINN());
                    ps.setInt(7, selectIDforNewPerson());
                    ps.executeUpdate();
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Can't insert person, rollback");
            e.printStackTrace();
            rollback();
        }
    }

    private void rollback()
    {
        try
        {
            connection.rollback();
        }
        catch (SQLException e)
        {
            System.out.println("Can't rollback");
            e.printStackTrace();
        }
    }
    private boolean insertAddress()
    {
        try {
            String requestInsertAddress = "Insert INTO address (postcode, country, region, city, street, house, flat) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(requestInsertAddress);
            ps.setString(1, String.valueOf(apiDataRead.getIndex()));
            ps.setString(2, apiDataRead.getDefaultCountry());
            ps.setString(3, apiDataRead.getRegion());
            ps.setString(4, apiDataRead.getCity());
            ps.setString(5, apiDataRead.getStreet());
            ps.setInt(6, apiDataRead.getHouse());
            ps.setInt(7, apiDataRead.getFlat());
            ps.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Can't insert address, rollback");
            e.printStackTrace();
            rollback();
            return false;
        }
    }
    private int selectIDforNewPerson()
    {
        int selectedID = 0;
        try {
            String query = "Select MAX(id) from address";
            PreparedStatement ps = connection.prepareStatement(query);
            resultSet = ps.executeQuery();
            while (resultSet.next())
                selectedID = resultSet.getInt(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return selectedID;

    }

    public boolean testIfEmpty()
    {

        try {
            String query = "Select * from persons";
            PreparedStatement ps = connection.prepareStatement(query);
            resultSet = ps.executeQuery();
            String string = "";
            while (resultSet.next())
                string = resultSet.getString("surname");
            return (string.equals(""));
        }
        catch (SQLException e)
        {
            System.out.println("Empty database, go to local files");
            return true;
        }

    }

    private int selectID(String surname, String name, String middlename, String birthdate)
    {
        try {
            String responce = "Select persons.address_id from persons where (name=? and surname=? and middlename=? and birthday =?)";
            PreparedStatement ps = connection.prepareStatement(responce);
            ps.setString   (1, name);
            ps.setString(2, surname);
            ps.setString(3, middlename);
            ps.setString(4, birthdate);
            resultSet = ps.executeQuery();
            int selectedID = 0;
            while (resultSet.next())
                selectedID = resultSet.getInt(1);
            return selectedID;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    private String getINN()
    {
        PersonalINNGenerator generatorINN = new PersonalINNGenerator();
        generatorINN.generate();
        return generatorINN.getStringINN();
    }

    private String getRightFormatDate(Date date)
    {
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        return (formatter.format(date));
    }

    private char getGender()
    {
        String thirdName = apiDataRead.getThirdName();
        if (thirdName.charAt(thirdName.length()-1)=='а') {
            return ('Ж');
        }
        else
        if (thirdName.charAt(thirdName.length()-1)=='ч') {
            return ('М');
        }
        else return ('?');
    }
}