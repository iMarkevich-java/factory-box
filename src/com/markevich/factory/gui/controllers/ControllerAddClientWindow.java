package com.markevich.factory.gui.controllers;

import businessObjectFactoryBox.Client;
import com.markevich.factory.gui.common.AppWindows;
import com.markevich.factory.gui.common.CheckConnect;
import com.markevich.factory.gui.common.DBWindow;
import com.markevich.factory.gui.common.WindowConfigs;
import com.markevich.factory.service.ServiceFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class ControllerAddClientWindow implements DBWindow {

    @FXML
    private TextField companyNameTextField;
    @FXML
    private TextArea legalDataTextField;
    @FXML
    private TextArea addressTextField;
    @FXML
    private TextField managerTextField;
    @FXML
    private Label companyNameLabel;
    @FXML
    private Label legalDataLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label managerLabel;
    @FXML
    private TableView<Client> tableAllClient;

    @FXML
    private void showMainWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.StartWindow);
        appWindows.reloadWindow(WindowConfigs.StartWindow);
    }

    @FXML
    private void showClientListWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.ClientListWindow);
            appWindows.reloadWindow(WindowConfigs.ClientListWindow);
        } else {
            showCheckConnectWindow();
        }

    }

    private void showCheckConnectWindow() {
        CheckConnect checkConnect = new CheckConnect();
        checkConnect.createWindow();
    }

    @FXML
    private void save() {
        if (checkConnect()) {
            Client client = new Client();
            client.setId("0");
            if (checkValueText()) {
                client.setAddress(addressTextField.getText());
                client.setManager(managerTextField.getText());
                client.setLegalData(legalDataTextField.getText());
                client.setCompanyName(companyNameTextField.getText());
                ServiceFactory.ClientServices().save(client);
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
    }

    private void clearSelectClient() {
        companyNameTextField.setText("");
        legalDataTextField.setText("");
        addressTextField.setText("");
        managerTextField.setText("");
        companyNameLabel.setText("");
        addressLabel.setText("");
        managerLabel.setText("");
        legalDataLabel.setText("");
    }

    private Boolean checkValueText() {
        boolean bool = true;

        if (companyNameTextField.getText().isEmpty()) {
            companyNameLabel.setText("Please enter text");
            bool = false;
        } else {
            companyNameLabel.setText("");
        }
        if (addressTextField.getText().isEmpty()) {
            addressLabel.setText("Please enter text");
            bool = false;
        } else {
            addressLabel.setText("");
        }
        if (managerTextField.getText().isEmpty()) {
            managerLabel.setText("Please enter text");
            bool = false;
        } else {
            managerLabel.setText("");
        }
        if (legalDataTextField.getText().isEmpty()) {
            legalDataLabel.setText("Please enter text");
            bool = false;
        } else {
            legalDataLabel.setText("");
        }
        return bool;
    }

    private Boolean checkConnect() {
        return ServiceFactory.ConnectService().connect().equals("OK");
    }

    @Override
    public void setData(String data) {
    }

    @Override
    public void reloadWindow() {
        List<Client> listClient = ServiceFactory.ClientServices().loadAll();
        if (!(listClient == null)) {
            ObservableList<Client> observableList;
            observableList = tableAllClient.getItems();
            observableList.clear();
            observableList.addAll(listClient);
            clearSelectClient();
        }
    }
}