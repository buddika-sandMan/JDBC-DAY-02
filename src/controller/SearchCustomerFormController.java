package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchCustomerFormController {

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
    void btnSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Customer customer = searchCustomer(txtID.getText());

        if(customer!=null){
            txtName.setText(customer.getName());
            txtAddrss.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
        } else {
            System.out.println("No customer data");
        }
    }

    public static Customer searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Statement statement = DBConnection.getInstance().getConnetion().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM customer WHERE id='" + id + "'");
        if(resultSet.next()){
            return new Customer(id, resultSet.getString("name"), resultSet.getNString("address"), resultSet.getDouble("salary"));
        }

        return null;
    }

}
