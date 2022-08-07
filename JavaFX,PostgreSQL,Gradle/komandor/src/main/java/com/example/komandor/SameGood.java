package com.example.komandor;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SameGood {

    SimpleStringProperty id;
    SimpleStringProperty name;
    SimpleIntegerProperty price;

    public SameGood(SimpleStringProperty id, SimpleStringProperty name, SimpleIntegerProperty price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public SameGood() {
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

}
