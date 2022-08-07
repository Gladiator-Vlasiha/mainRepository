package com.example.komandor;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Order {

    SimpleStringProperty nameInBasket;
    SimpleIntegerProperty priceInBasket;
    SimpleIntegerProperty countInBasket;
    SimpleIntegerProperty totalInBasket;


    public Order(String nameInBasket, int priceInBasket, int countInBasket, int totalInBasket) {
        this.nameInBasket = new SimpleStringProperty(nameInBasket);
        this.priceInBasket = new SimpleIntegerProperty(priceInBasket);
        this.countInBasket = new SimpleIntegerProperty(countInBasket);
        this.totalInBasket = new SimpleIntegerProperty(totalInBasket);

    }

    public String getNameInBasket() {
        return nameInBasket.get();
    }

    public SimpleStringProperty nameInBasketProperty() {
        return nameInBasket;
    }

    public void setNameInBasket(String nameInBasket) {
        this.nameInBasket.set(nameInBasket);
    }

    public int getPriceInBasket() {
        return priceInBasket.get();
    }

    public SimpleIntegerProperty priceInBasketProperty() {
        return priceInBasket;
    }

    public void setPriceInBasket(int priceInBasket) {
        this.priceInBasket.set(priceInBasket);
    }

    public int getCountInBasket() {
        return countInBasket.get();
    }

    public SimpleIntegerProperty countInBasketProperty() {
        return countInBasket;
    }

    public void setCountInBasket(int countInBasket) {
        this.countInBasket.set(countInBasket);
    }

    public int getTotalInBasket() {
        return totalInBasket.get();
    }

    public SimpleIntegerProperty totalInBasketProperty() {
        return totalInBasket;
    }

    public void setTotalInBasket(int totalInBasket) {
        this.totalInBasket.set(totalInBasket);
    }

    public Order() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(nameInBasket, order.nameInBasket) && Objects.equals(priceInBasket, order.priceInBasket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameInBasket, priceInBasket);
    }
}
