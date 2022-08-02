import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
//**
@Entity
@Table(name = "Students")

public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int age;


    private String name;

    @Column(name = "registration_date")
    private Date registrationDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "student")
    private List<Subscription> subscriptions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "student")
    private List<Purchaselist> purchaselist;

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public List<Purchaselist> getPurchaselist() {
        return purchaselist;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void setPurchaselist(List<Purchaselist> purchaselist) {
        this.purchaselist = purchaselist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public String getNameFomId(int id) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
