package com.example.komandor;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.komandor.MainController.*;
import static com.example.komandor.MainController.getListOrderInBasket;
import static com.example.komandor.Storage.*;


public class PayController {
    @FXML
    private TextField payBankField;

    @FXML
    private Text messagePayText;


    @FXML
    protected void sendToBank() throws IOException, InterruptedException, SQLException {

        int paySum = Integer.parseInt(payBankField.getText());
        int total = getTotalPriceSameOrder();

        if (paySum == total) {

            addCheckInTableChecks(new Date(),total);

            //getListOrderInBasket().forEach(s-> System.out.println(s.getNameInBasket()));
            //getOrderTableView();
            //System.out.println("размер списка заказов"+orderTableView.getItems().size());

            //addCheckInTableChecklines(0,1,1,1,1);
            addCheckInTableChecklines(getListOrderInBasket());

            clearListOrderInBasket();
            closeButtonAction();
            clearTotalPriceSameOrder();
            clearSumFieldInMainPage();


        } else if (paySum > total) {
            messagePayText.setText("Вы ввели бОльшую сумму чем необходимо,сдачи мы не даем=) исправьте пожалуйста");
        } else if (paySum < total) {
            messagePayText.setText("Вы ввели меньшую сумму чем необходимо,исправьте пожалуйста");
        }
    }

    private void clearSumFieldInMainPage() {
        //todo очистка суммы корзины
    }

    private void closeButtonAction() {
//        Thread.sleep(1000);
//        payBankField.setText("Pay done!");
//        messagePayText.setText("платеж прошел успешно");
        Stage stage = (Stage) payBankField.getScene().getWindow();
        stage.close();
    }

}
