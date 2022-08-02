import javax.persistence.*;
import java.util.Date;

//**
@Entity
@Table(name = "subscriptions")
public class Subscription {
    @EmbeddedId
    private KeySub id;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    //@Column(name = "student_id", insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    //@Column(name = "course_id", insertable = false, updatable = false)
    private Course course;


    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public Subscription(Student student, Course course, Date subscriptionDate) {
        this.student = student;
        this.course = course;
        this.subscriptionDate = subscriptionDate;
    }

    public KeySub getId() {
        return id;
    }

    public void setId(KeySub id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourseId(int courseId) {
        this.course = course;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Subscription(KeySub id) {
        this.id = id;
    }

    protected Subscription() {
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "student=" + student.getName() + "   " +
                ", course=" + course.getName() +  "   " +
                ", subscriptionDate=" + subscriptionDate  +
                '}'+"\n";
    }
}
