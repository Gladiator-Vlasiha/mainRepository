import org.hibernate.annotations.Target;
import org.jboss.logging.Field;

import javax.persistence.*;
import java.util.Date;

//**
@Entity
@Table(name ="purchaselist")
public class Purchaselist {


    @EmbeddedId
    private KeyPurch id;

    @ManyToOne
    @JoinColumn(name = "student_name", insertable = false, updatable = false,referencedColumnName= "name")
    private Student student;


    @ManyToOne
    @JoinColumn(name = "course_name", insertable = false, updatable = false,referencedColumnName= "name")
    private Course course;

    private int price;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public Purchaselist(Student student, Course course, int price, Date subscriptionDate) {
        this.student = student;
        this.course = course;
        this.price = price;
        this.subscriptionDate = subscriptionDate;
    }



    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Student getStudent() {
        return student;
    }

    public KeyPurch getId() {
        return id;
    }

    public void setId(KeyPurch id) {
        this.id = id;
    }
    protected Purchaselist() {
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Purchaselist{" +
                "student=" + student.getName() +
                ", course=" + course.getName() +
                ", price=" + price +
                ", subscriptionDate=" + subscriptionDate +
                '}'+"\n";
    }
}
