import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {
    List<Station> routeFruit;
    List<Station> metroNsk;
    List<Station> metro = new ArrayList<>();

    // private Station from;
    // private Station to;

    @Override
    protected void setUp() throws Exception {
        routeFruit = new ArrayList<>();
        Line line1 = new Line(1, "Фрукты");
        Line line2 = new Line(2, "Овощи");

        routeFruit.add(new Station("Апельсиновая", line1));
        routeFruit.add(new Station("Арбузная", line1));
        routeFruit.add(new Station("Морковная", line2));
        routeFruit.add(new Station("Капустная", line2));
    }
    //test 1

    public void testGetShortestRoute() {
        metroNsk = new ArrayList<>();
        //  from=route.get(1);
        //  to=route.get(route.size()-1);
//        String actual= String.valueOf(route);
//        String expected =" ";//"[Петровская, Арбузная, Морковная, Яблочная]";
//        assertEquals(expected,actual);
        RouteCalculator routeCalculator = new RouteCalculator(new StationIndex());
        Line line1 = new Line(1, "Ленинская линия");
        Station one = new Station("Площадь Маркса", line1);
        Station two = new Station("Студенческая", line1);
        Station three = new Station("Речной вокзал", line1);
        Station four = new Station("Октябрьская", line1);
        Station five = new Station("Площадь ленина", line1);

        line1.addStation(one);
        line1.addStation(two);
        line1.addStation(three);
        line1.addStation(four);
        line1.addStation(five);

        metroNsk.add(one);
        metroNsk.add(two);
        metroNsk.add(three);
        metroNsk.add(four);
        metroNsk.add(five);
        //System.out.println("***");
        String actual = String.valueOf(routeCalculator.getShortestRoute(one, four));
        String expected = "[Площадь Маркса, Студенческая, Речной вокзал, Октябрьская]";
        assertEquals("Результат testGetShortestRoute -", expected, actual);


    }

    //test 2
    public void testCalculateDuration() {
        double actual = RouteCalculator.calculateDuration(routeFruit);
        double expected = 8.5;
        assertEquals("Результат testCalculateDuration -", expected, actual);
    }

    //test 3
    public void testGetRouteWithOneConnection() {
        metroNsk = new ArrayList<>();
        StationIndex stationIndex = new StationIndex();
        List<Station> connectionStations = new ArrayList<>();
        RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
        Line line1 = new Line(1, "Ленинская линия");
        Station one = new Station("Заельцовская", line1);
        Station two = new Station("Гагаринская", line1);
        Station three = new Station("Красный проспект", line1);
        Station four = new Station("Площадь ленина", line1);
        Station five = new Station("Октябрьская", line1);

        Line line2 = new Line(2, "Дзержинская линия");
        Station one2 = new Station("Сибирская", line2);
        Station two2 = new Station("Покрышкина", line2);

        line1.addStation(one);
        line1.addStation(two);
        line1.addStation(three);//пересадка 1-2 ветка
        line1.addStation(four);
        line1.addStation(five);
        line2.addStation(one2);//пересадка 2-1 ветка
        line2.addStation(two2);

        metroNsk.add(one);
        metroNsk.add(two);
        metroNsk.add(three);
        metroNsk.add(four);
        metroNsk.add(five);
        metroNsk.add(one2);
        metroNsk.add(two2);
        //блок пересадок
        connectionStations.add(three);
        connectionStations.add(one2);

        stationIndex.addConnection(connectionStations);
        //System.out.println("**********");
        String actual = String.valueOf(routeCalculator.getShortestRoute(one, two2));
        String expected = "[Заельцовская, Гагаринская, Красный проспект, Сибирская, Покрышкина]";
        assertEquals("Результат testGetRouteWithOneConnection -", expected, actual);

    }


    //test 4
    public void testGetRouteWithTwoConnections() {

        StationIndex stationIndex;//=new StationIndex();
        List<Station> connectionStationsOne = new ArrayList<>();
        List<Station> connectionStationsTwo = new ArrayList<>();
        RouteCalculator routeCalculator = new RouteCalculator(stationIndex = new StationIndex());
        Line line1 = new Line(1, "Ленинская линия");
        Station one = new Station("Заельцовская", line1);
        Station two = new Station("Гагаринская", line1);
        Station three = new Station("Красный проспект", line1);
        Station four = new Station("Площадь ленина", line1);
        Station five = new Station("Октябрьская", line1);

        Line line2 = new Line(2, "Дзержинская линия");
        Station one2 = new Station("Сибирская", line2);
        Station two2 = new Station("Покрышкина", line2);

        Line line3 = new Line(3, "Третья линия");
        Station one3 = new Station("Начальная", line3);
        Station two3 = new Station("Конечная", line3);

        line1.addStation(one);
        line1.addStation(two);
        line1.addStation(three);//пересадка 1-2 ветка
        line1.addStation(four);
        line1.addStation(five);
        line2.addStation(one2);//пересадка 2-1 ветка
        line2.addStation(two2);//пересадка 2-3 ветка
        line3.addStation(one3);//пересадка 3-2 ветка
        line3.addStation(two3);

        metro.add(one);
        metro.add(two);
        metro.add(three);
        metro.add(four);
        metro.add(five);
        metro.add(one2);
        metro.add(two2);
        metro.add(one3);
        metro.add(two3);
        //блок пересадок
        connectionStationsOne.add(three);
        connectionStationsOne.add(one2);
        connectionStationsTwo.add(two2);
        connectionStationsTwo.add(one3);

        stationIndex.addConnection(connectionStationsOne);
        stationIndex.addConnection(connectionStationsTwo);
        //System.out.println("**********");

        String actual = String.valueOf(routeCalculator.getShortestRoute(one, two3));
        String expected = "[Заельцовская, Гагаринская, Красный проспект, Сибирская, Покрышкина, Начальная, Конечная]";//"[]";
        assertEquals("Результат testGetRouteWithTwoConnections -", expected, actual);
    }


    //test 5
    public void testIsConnected() {
    }

    //test 6
    public void testGetRouteViaConnectedLine() {
    }
    //test 7
    public void testGetRouteOnTheLine() {

    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
