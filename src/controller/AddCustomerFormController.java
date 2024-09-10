package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
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

    Stage stage = new Stage();

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        Customer customer = new Customer(
                txtID.getText(),
                txtName.getText(),
                txtAddrss.getText(),
                Double.parseDouble(txtSalary.getText())
        );

//        String id = txtID.getText();
//        String name = txtName.getText();
//        String address = txtAddrss.getText();
//        Double salary = Double.parseDouble(txtSalary.getText());

//        String SQL = "INSERT INTO customer VALUES('"+id+"','"+name+"','"+address+"',"+salary+")";
        //prepared statement
        String SQL = "INSERT INTO customer VALUES(?,?,?,?)";

        try {
            Connection connection = db.DBConnection.getInstance().getConnetion();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, customer.getId());
            preparedStatement.setObject(2, customer.getName());
            preparedStatement.setObject(3, customer.getAddress());
            preparedStatement.setObject(4, customer.getSalary());
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

    @FXML
    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/search_customer_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(txtID.getText(), txtName.getText(), txtAddrss.getText(), Double.parseDouble(txtSalary.getText()));
        boolean isUpdate = updateCustomer(customer);

        if(isUpdate){
            System.out.println("updated");
        } else {
            System.out.println("not updated.");
        }

    }

    public static boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Connection connection = db.DBConnection.getInstance().getConnetion();
        String SQL = "UPDATE customer set name=?, address=?, salary=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, customer.getName());
        preparedStatement.setObject(2, customer.getAddress());
        preparedStatement.setObject(3, customer.getSalary());
        preparedStatement.setObject(4, customer.getId());
        int res = preparedStatement.executeUpdate();
        if(res>0) {
            return true;
        }
        connection.close();
        return false;
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean isDeleted = deleteCustomer(txtID.getText());

        if(isDeleted){
            System.out.println("deleted");
        } else {
            System.out.println("not deleted.");
        }
    }

    public static boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnetion()
                .createStatement().executeUpdate("DELETE FROM customer WHERE id='"+id+"'") > 0;
    }
}
