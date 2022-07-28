package com.example.komandor;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    String input;
    int totalPriceSameOrder = 0;
    ObservableList<Goods> listGoods = FXCollections.observableArrayList();
    ObservableList<Goods> listGoodsInBasket = FXCollections.observableArrayList();
    ObservableList<Order> listOrderInBasket = FXCollections.observableArrayList();


    private ObservableList<Goods> initGoods() {
        listGoods.add(new Goods("cappuccino", 100));
        listGoods.add(new Goods("latte", 50));
        listGoods.add(new Goods("espresso", 5000));
        listGoods.add(new Goods("cold water", 15));
        listGoods.add(new Goods("milk", 1));
        listGoods.add(new Goods("hot water", 90));
        return listGoods;
    }


    @FXML
    private Label welcomeText;

    @FXML
    private TextField searchField;

    @FXML
    private TextField textField;

    @FXML
    private TextField windowSum=textField;
    @FXML
    private TableColumn<Goods, String> columnName;
    @FXML
    private TableColumn<Goods, Integer> columnPrice;
    @FXML
    private TableColumn<Order, String> nameInBasket;
    @FXML
    private TableColumn<Order, Integer> priceInBasket;
    @FXML
    private TableColumn<Order, Integer> countInBasket;
    @FXML
    private TableColumn<Order, Integer> totalInBasket;

    @FXML
    private TableView<Goods> goodsTableView = new TableView<>();
    @FXML
    private TableView<Order> orderTableView = new TableView<>();

    @FXML
    private void initialize() {
        columnName.setCellValueFactory(new PropertyValueFactory<Goods, String>("name"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("price"));
        nameInBasket.setCellValueFactory(new PropertyValueFactory<Order, String>("nameInBasket"));
        priceInBasket.setCellValueFactory(new PropertyValueFactory<Order, Integer>("priceInBasket"));
        countInBasket.setCellValueFactory(new PropertyValueFactory<Order, Integer>("countInBasket"));
        totalInBasket.setCellValueFactory(new PropertyValueFactory<Order, Integer>("totalInBasket"));

        initGoods();

    }


    @FXML
    protected void onPayButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("payPage.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        windowSum.setText(String.valueOf(textField));

        //welcomeText.setText("Pay done!");
    }

    @FXML
    protected void onSearchInput() {
        input = searchField.getText();
        System.out.println(searchField.getText());
        welcomeText.setText(input);
    }

    @FXML
    protected String searchGoods() {
        ObservableList<Goods> listSameGoods = FXCollections.observableArrayList();
        input = searchField.getText();
        listGoods.forEach(s -> {
            if (s.getName().contains(input)) {
                listSameGoods.add(s);
                System.out.println(searchField.getText());
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(input);

            }
        });
        TableView.TableViewSelectionModel<Goods> selectionModel = goodsTableView.getSelectionModel();
        goodsTableView.setItems(listSameGoods);
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("we check " + newValue.getName() + ", this price=" + newValue.getPrice());
            //todo если добавлять товары с разных поисков задваиваютсяь позиции
            //todo реализовать возможность добавления одного и тогоже товара подряд каждым нажатием(в текущей версии подряд одна и таже позиция не добавляеться)
            Order order = new Order("default", 1, 1, 1);
            order.setNameInBasket(newValue.getName());
            order.setPriceInBasket(newValue.getPrice());
            order.setCountInBasket(1);
            order.setTotalInBasket(newValue.getPrice() * order.getCountInBasket());
            if (listOrderInBasket.isEmpty()) {
                System.out.println("list empty,add order");
                orderTableView.setItems(listOrderInBasket);
                listOrderInBasket.add(order);
                totalPriceSameOrder = totalPriceSameOrder + order.getPriceInBasket();
                textField.setText(String.valueOf(totalPriceSameOrder));
                return;
            } else {
                System.out.println("list NOT empty,NOT add order");
                listOrderInBasket.forEach(s -> {
                    if (s.getNameInBasket().matches(newValue.getName())) {
                        int oldCount = s.getCountInBasket();
                        listOrderInBasket.remove(s);
                        Order orderNew = new Order("default", 1, 1, 1);
                        orderNew.setNameInBasket(newValue.getName());
                        orderNew.setPriceInBasket(newValue.getPrice());
                        orderNew.setCountInBasket(oldCount + 1);
                        orderNew.setTotalInBasket(newValue.getPrice() * orderNew.getCountInBasket());
                        listOrderInBasket.add(orderNew);
                        totalPriceSameOrder = totalPriceSameOrder + order.getPriceInBasket();
                        textField.setText(String.valueOf(totalPriceSameOrder));
                        System.out.println(" find clone");
                        orderTableView.setItems(listOrderInBasket);
                        return;
                    }

                });
                System.out.println("this order dont clone");
                listOrderInBasket.add(order);
                totalPriceSameOrder = totalPriceSameOrder + order.getPriceInBasket();
                orderTableView.setItems(listOrderInBasket);
                textField.setText(String.valueOf(totalPriceSameOrder));
                return;
            }
        });

        return input;
    }

}