import com.jfoenix.controls.JFXTextField;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;

public class cont_CustomerDirectory {

    @FXML
    public TableColumn col_1,col_2,col_3,col_4, col_5;
    private ObservableList<TableModel_Customer> customer_data;
    @FXML
    TableView customer_list;
    @FXML
    JFXTextField filter_name,filter_phone,filter_email;

    //set up DB connection
    private Connection connect_db() {
       /* SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("<user>");
        ds.setPassword("<password>");
        ds.setServerName("<server>");
        ds.setPortNumber(1400);
        ds.setDatabaseName("AdventureWorks");

        CODE ABOVE IS FOR REMOTE CONNECTION */


       //CODE FOR LOCAL SERVER CONNECTION
        String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=AdventureWorks;user=MyUserName;password=*****";

        Connection con = null;

        try{
            con = DriverManager.getConnection(url);
            //con = ds.getConnection();
            con.setAutoCommit(false);
            System.out.println("Opened database successfully");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    private void populateDataTable() {

        Connection conn = this.connect_db();
        customer_data = FXCollections.observableArrayList();

        String sql_main = "SELECT * FROM Customers ORDER BY Customer_ID";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql_main);
            ResultSet result_set = preparedStatement.executeQuery();
            while (result_set.next()) {
                customer_data.add(new TableModel_Customer(result_set.getInt(1),result_set.getString(2),result_set.getString(3),result_set.getString(4),
                        result_set.getString(5)));
            }

            result_set.close();
            conn.close();
        } catch (SQLException tableQueryException) {
            System.err.println(tableQueryException.toString());
        }
    }




}
