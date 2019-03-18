import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class EditorSQL {

    // JDBC URL, username and password of your MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/fintech?useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    // Default requests
    private String requestUpdateAddress = "UPDATE address set postcode= ? and country= ? and region= ? and city= ? and street= ? and house= ? and flat= ?" +
            " where id= ?";
    private String requestInsertAddress = "Insert INTO address (postcode, country, region, city, street, house, flat) VALUES(?,?,?,?,?,?,?)";
    private String requestInsertPerson = "Insert INTO persons (name, surname, middlename, birthday, gender, inn,  address_id) VALUES(?,?,?,?,?,?,?)";


    private IteratorSQL iteratorSQL;
    private ApiDataRead apiDataRead;

    EditorSQL()
    {
        apiDataRead = new ApiDataRead();
    }

    void fill(int maxPersonNumber) {
        try {
            con = DriverManager.getConnection(url, user, password);
            // disable autocommit
            con.setAutoCommit(false);
            // getting Statement object to execute query
            stmt = con.createStatement();

            for (int i=0;i<maxPersonNumber;i++) {
                // executing SELECT query
                apiDataRead.setNewRandomJSON();
                insertPerson();
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            //commit, close connection ,stmt and result set here
           try {
               con.commit();
            } catch (SQLException se) { /*can't do anything }*/}
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
    }

    void export() {
        iteratorSQL = new IteratorSQL(url, user, password);
        ExcelExport excelExport = new ExcelExport();
        excelExport.createFromSQL(iteratorSQL);
        PdfExport pdfExport = new PdfExport();
        pdfExport.createWithSQL(iteratorSQL);
        iteratorSQL.close();
    }

    boolean connect()
    {
        try {
            try {
                // opening database connection to MySQL server
                con = DriverManager.getConnection(url, user, password);
                return true;
            }
            catch (CommunicationsException e)
            {
                System.out.println("Couldn't connect to database");
                return false;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }

    private boolean tryToUpdate()
    {
        try {
            int findingID = selectID(apiDataRead.getSecondName(),apiDataRead.getFirstName(),apiDataRead.getThirdName(),getRightFormatDate(apiDataRead.getRandomBirthDate()));
            if (findingID!=0) {
                PreparedStatement ps = con.prepareStatement(requestUpdateAddress);
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
                    PreparedStatement ps = con.prepareStatement(requestInsertPerson);
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
            con.rollback();
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
            PreparedStatement ps = con.prepareStatement(requestInsertAddress);
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
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next())
                selectedID = rs.getInt(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return selectedID;

    }

    boolean testIfEmpty()
    {

        try {
            String query = "Select * from persons";
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            String string = "";
            while (rs.next())
                string = rs.getString("surname");
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
            PreparedStatement ps = con.prepareStatement(responce);
            ps.setString   (1, name);
            ps.setString(2, surname);
            ps.setString(3, middlename);
            ps.setString(4, birthdate);
            rs = ps.executeQuery();
            int selectedID = 0;
            while (rs.next())
                selectedID = rs.getInt(1);
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


