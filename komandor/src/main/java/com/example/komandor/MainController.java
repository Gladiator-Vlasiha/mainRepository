package com.example.komandor;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.ArrayList;

public class MainController {
    String input;
    ArrayList<String> arrayList = new ArrayList<>();
    ObservableList<Goods> listGoods = FXCollections.observableArrayList();
    ObservableList<Goods> listGoodsInBasket = FXCollections.observableArrayList();


    private void initArray() {
        arrayList.add("Java");
        arrayList.add("JavaScript");
        arrayList.add("C#");
        arrayList.add("Python");
    }


    private ObservableList<Goods> initGoods() {
        listGoods.add(new Goods("капучино", 100));
        listGoods.add(new Goods("вода", 50));
        listGoods.add(new Goods("водка", 5000));
        listGoods.add(new Goods("возможность попить", 99999999));
        listGoods.add(new Goods("пиво", 1));
        listGoods.add(new Goods("латте", 90));
        return listGoods;
    }


    @FXML
    private Label welcomeText;

    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<Goods, String> columnName;
    @FXML
    private TableColumn<Goods, Integer> columnPrice;
    @FXML
    private TableColumn<Goods, Integer> nameInBasket;
    @FXML
    private TableColumn<Goods, Integer> priceInBasket;
    @FXML
    private TableColumn<Goods, Integer> countInBasket;
    @FXML
    private TableColumn<Goods, Integer> totalInBasket;

    @FXML
    private TableView<Goods> tableView=new TableView<>();
    @FXML
    private TableView<Goods> basket=new TableView<>();

    @FXML
    private void initialize() {
        columnName.setCellValueFactory(new PropertyValueFactory<Goods, String>("name"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("price"));
        initGoods();
        //tableView.setItems(initGoods());
    }


    @FXML
    protected void onPayButtonClick() {
        welcomeText.setText("Pay done!");
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
        TableView.TableViewSelectionModel<Goods> selectionModel = tableView.getSelectionModel();
        tableView.setItems(listSameGoods);
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("we check "+newValue.getName()+", this price="+newValue.getPrice());
            listGoodsInBasket.add(newValue);
            basket.setItems(listGoodsInBasket);
        });
        return input;
    }

}