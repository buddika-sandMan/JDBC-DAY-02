package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.*;

public class AddCustomerFormController {

    @FXML
    private TextField txtAddrss;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddrss.getText();
        Double salary = Double.parseDouble(txtSalary.getText());

//        String SQL = "INSERT INTO customer VALUES('"+id+"','"+name+"','"+address+"',"+salary+")";
        //prepared statement
        String SQL = "INSERT INTO customer VALUES(?,?,?,?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/thogakade", "root", "root");
//            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, id);
            preparedStatement.setObject(2, name);
            preparedStatement.setObject(3, address);
            preparedStatement.setObject(4, salary);
            int res = preparedStatement.executeUpdate();
            if(res>0) {
                System.out.println("Saved");
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
