import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "LinkedPurchaseList")
public class LinkedPurchaseList implements Serializable {

    @EmbeddedId
    private KeyLink linkKey;

    private LinkedPurchaseList(){}

    public LinkedPurchaseList(int courseId, int studentId) {
        this.linkKey = new KeyLink(courseId, studentId);
    }

    @Override
    public String toString() {
        return linkKey.toString();
    }

    @Data
    @Embeddable
    public static class KeyLink implements Serializable {

        @Column(name = "course_id")
        int courseId;

        @Column(name = "student_id")
        int studentId;
        protected KeyLink() {}

        public KeyLink(int courseId, int studentId) {
            this.courseId = courseId;
            this.studentId = studentId;
        }

        @Override
        public String toString() {
            return "Course Id = " + courseId + "\tStudent Id = " + studentId;
        }
    }

}
