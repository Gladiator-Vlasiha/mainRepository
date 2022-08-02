import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Calendar.MONTH;
import static org.hibernate.id.PersistentIdentifierGenerator.PK;
//**
public class Main {
    public static void main(String[] args) {
        System.out.println("1");
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        //todo задание 16.10
//       Course course = session.get(Course.class,1);
//       List<Student> students=course.getStudents();
//        students.forEach(s-> System.out.println(s.getName()+"\n"+s.getSubscriptions()+"\n"+s.getPurchaselist().toString()+"\n"));

        //todo задание 16.11
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Course> criteriaQuery=criteriaBuilder.createQuery(Course.class);
//        Root<Course>root=criteriaQuery.from(Course.class);
//        criteriaQuery.select(root);
//        List<Course> courseList=session.createQuery(criteriaQuery).getResultList();
//        courseList.forEach(s-> System.out.println(s.getName()));

        //todo задание 16.15
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<LinkedPurchaseList> criteriaQuery=criteriaBuilder.createQuery(LinkedPurchaseList.class);
        Root<LinkedPurchaseList>root=criteriaQuery.from(LinkedPurchaseList.class);
        criteriaQuery.select(root);
        List<LinkedPurchaseList> linkList=session.createQuery(criteriaQuery).getResultList();
        linkList.forEach(System.out::println);


        sessionFactory.close();
    }
//        String url = "jdbc:mysql://localhost:3306/skillbox?useSSL=false&serverTimezone=UTC";
//        String user = "root";
//        String password = "testtest";
//        try {
//            Connection connection = DriverManager.getConnection(url, user, password);
//            Statement statement = connection.createStatement();
//
//            HashMap<String, Map<Integer, Integer>> mapCourses = new HashMap<>();
//            HashMap<Integer, Integer> mapDates;//= new HashMap<>();
//            ResultSet resultSet = statement
//                    .executeQuery("SELECT course_name,MONTH(subscription_date) FROM PurchaseList");
//            while (resultSet.next()) {
//
//                mapDates = new HashMap<>();
//
//                String name = resultSet.getString("course_name");
//                int month = Integer.parseInt(resultSet.getString("MONTH(subscription_date)"));
//                // System.out.println("получаем на вход---------------------------" + name + " - " + month);
//                //если
//
//                if (!mapCourses.containsKey(name)) {
//                    mapDates.put(month, 1);
//                    mapCourses.put(name, mapDates);
//                } else if (mapCourses.get(name).containsKey(month)) {
//                    int i = mapCourses.get(name).get(month);
//                    i++;
//                    mapCourses.get(name).replace(month, i);
//                } else if (!mapCourses.get(name).containsKey(month)) {
//                    mapCourses.get(name).put(month, 1);
//                }
//            }
//            //если курс уже есть в списке
//
//
//            System.out.println("***");
//            //выводим  среднее количество покупок в месяц для каждого курса
//
//            mapCourses.forEach((k, v) -> {
//                String r = "Среднее количество покупок курса  «" + k + "» : \n";
//                System.out.println("Среднее количество покупок курса  «" + k + "» : \n"
//                        + v.entrySet().stream().map(e -> "номер месяца " + e.getKey()
//                        + " -усредненные продажи " + truncateDecimal(e.getValue() / 9.0, 2) + "\n")
//                        .collect(Collectors.toList())
//                );
//            });
//
//            System.out.println("***");
//
//            connection.close();
//            resultSet.close();
//            statement.close();
//
//
//        } catch (
//                Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private static BigDecimal truncateDecimal(double x, int numberofDecimals) {
//        if (x > 0) {
//            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
//        } else {
//            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
//        }
//    }
}
