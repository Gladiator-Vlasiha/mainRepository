package com.example.komandor;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
//import org.apache.log4j.Logger;

public class MainController {
    //private static Logger logger = Logger.getRootLogger();
    String input;
    static int totalPriceSameOrder = 0;

    ObservableList<Goods> listGoods = FXCollections.observableArrayList();

    public static ObservableList<Order> getListOrderInBasket() {
        return listOrderInBasket;
    }

    static ObservableList<Order> listOrderInBasket = FXCollections.observableArrayList();


    private ObservableList<Goods> initGoods() {
        Storage storage = new Storage();


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
    private TextField sumFieldInMainPage;

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
    protected void onPayButtonClick() throws IOException, InterruptedException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("payPage.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    protected void onDropButtonClick(ActionEvent event) throws IOException, InterruptedException {
        int deletedCost = orderTableView.getSelectionModel().getSelectedItem().getTotalInBasket();
        orderTableView.getItems().removeAll(orderTableView.getSelectionModel().getSelectedItem());
        orderTableView.getSelectionModel().clearSelection();
        int newTotalPriceSameOrder = totalPriceSameOrder - deletedCost;
        totalPriceSameOrder = newTotalPriceSameOrder;
        sumFieldInMainPage.setText(String.valueOf(totalPriceSameOrder));

    }


    @FXML
    protected void onSearchInput() {
        input = searchField.getText();
        welcomeText.setText(input);
    }

    @FXML
    protected String searchGoods() {
        ObservableList<Goods> listSameGoods = FXCollections.observableArrayList();
        input = searchField.getText();
        listGoods.forEach(s -> {
            if (s.getName().contains(input)) {
                listSameGoods.add(s);
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(input);

            }
        });
        TableView.TableViewSelectionModel<Goods> selectionModel = goodsTableView.getSelectionModel();
        goodsTableView.setItems(listSameGoods);
        Goods goods=goodsTableView.getSelectionModel().getSelectedItem();

        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //logger.info("we check " + newValue.getName() + ", this price=" + newValue.getPrice());
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
                sumFieldInMainPage.setText(String.valueOf(totalPriceSameOrder));
                goodsTableView.getSelectionModel().clearSelection();
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
                        sumFieldInMainPage.setText(String.valueOf(totalPriceSameOrder));
                        System.out.println(" find clone");
                        orderTableView.setItems(listOrderInBasket);
                        goodsTableView.getSelectionModel().clearSelection();
                        return;
                    }

                });
                System.out.println("this order dont clone");
                listOrderInBasket.add(order);
                totalPriceSameOrder = totalPriceSameOrder + order.getPriceInBasket();
                orderTableView.setItems(listOrderInBasket);
                sumFieldInMainPage.setText(String.valueOf(totalPriceSameOrder));
                goodsTableView.getSelectionModel().clearSelection();
                return;
            }
        });
        return input;
    }

    static void clearListOrderInBasket() {
        listOrderInBasket.clear();
    }

    static void clearTotalPriceSameOrder() {
        totalPriceSameOrder = 0;
        //sumFieldInMainPage.clear();
//        clearSumFieldInMainPage();
    }

    private void clearSumFieldInMainPage() {
        sumFieldInMainPage.clear();
    }

    public TextField getSumFieldInMainPage() {
        return sumFieldInMainPage;
    }

    public static int getTotalPriceSameOrder() {
        return totalPriceSameOrder;
    }

}