import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * synchronized (historySiteLinks){}
 *
 * */


public class LinksInSite extends RecursiveTask<ArrayList> {

    private static ArrayList<String> mainListUrl;
    private static TreeMap<String, TreeMap> treeMapMainSite;
    private static TreeMap<String, TreeMap> treeMapSite;
    private static HashSet<String> hashSetForClear;
    private static HashSet<String> historySiteLinks = new HashSet<>();
    private static ArrayList<ArrayList> result = new ArrayList<ArrayList>();
    private static AtomicInteger count = new AtomicInteger(0);
    public static String site = "skillbox.ru";
    public static String in ="";
    //public static String site = "arton-nsk.ru";
    //public static String site = "poloniex.com";
    //public static String site = "lenta.ru";
    private static String myDir = "C:\\Java_Skill\\gitlab\\java_basics\\java_basics\\java_basics\\java_basics\\java_basics\\Multithreading\\ForkJoinPool\\data";
    private static String myLog = "C:\\Java_Skill\\gitlab\\java_basics\\java_basics\\java_basics\\java_basics\\java_basics\\Multithreading\\ForkJoinPool\\log";

    private String string;

    public LinksInSite(String in) {
        this.in = in;
    }

    @Override
    protected ArrayList compute() {//здесь разворачивается основное действо
        System.out.println("номер итерации" + count.incrementAndGet());
        synchronized (historySiteLinks) {
            historySiteLinks.add(in);
        }
        List<LinksInSite> taskList = new ArrayList<>();
        try {
            ArrayList<String> tryLinks = checkDomain(getAllLinkInMain(site));
            if (tryLinks.size() > 0) {//проверяем,есть ли ссылки для парсинга на этой странице
                tryLinks.forEach(s -> {
                    if (!(historySiteLinks.contains(s))) {//проверяем,есть ли строка из нового списка в истории
                        synchronized (historySiteLinks) {
                            historySiteLinks.add(s);
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        LinksInSite task=new LinksInSite(s);
                        task.fork();
                        taskList.add(task);
                    }
                });
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        for(LinksInSite task:taskList){
            result.add(task.join());
        }
        System.out.println(result.size()+"---"+result.toString());
        return result;
    }

    // получаем все ссылки в консоль
    public static ArrayList<String> getAllLinkInMain(String site) throws IOException {
        // System.out.println("получаем все ссылки");
        mainListUrl = new ArrayList<>();
        Document offlineCopyMainSite = Jsoup.connect("https://" + site).maxBodySize(0).get();
        Elements string = offlineCopyMainSite.getAllElements();
        ArrayList<String> listString = new ArrayList<>();
        listString.add(string.toString());
        saveMainLink(listString, "copy_site");

        ArrayList<String> same = new ArrayList<>();
        Elements elements = offlineCopyMainSite.select("a");
        for (Element element : elements) {
            same.add(element.absUrl("href"));
        }
        saveMainLink(same, "ALL_links");
        return same;
    }

    // сохраняем ссылки в файл
    public static void saveMainLink(ArrayList<String> arrayList, String nameMethod) throws IOException {
//        HashSet<String> hashSet = new HashSet<>();
//        hashSet.addAll(arrayList);
//        arrayList.clear();
//        arrayList.addAll(hashSet);
//        //hashSet.forEach(s-> System.out.println(s));
//
//        for (int i = 0; i < hashSet.size(); i++) {
//            Files.write(Path.of(Path.of(myDir) + "/" + site.replaceAll("\\W", "_") + "_" + nameMethod + ".txt"), Collections.synchronizedList(arrayList));
//        }
//        System.out.println("save map of site complete.");
    }


    // метод отсеивание ссылок на другие сайты и поддомены,
    public static ArrayList<String> checkDomain(ArrayList<String> input) throws IOException {
        input.forEach(s -> {//удаляем все внешние ссылки
            String element = String.valueOf(s);
            Pattern pattern = Pattern.compile("\\W" + site);
            Matcher matcher = pattern.matcher(element);
            while (matcher.find()) {
                if (s.endsWith("/")) {
                    String[] strings = s.split("\\.");
                    if (strings.length == 2) {
                        mainListUrl.add(s);
                    }
                }
            }
        });
        hashSetForClear = new HashSet<>();//убираем задвоения
        hashSetForClear.addAll(mainListUrl);
        mainListUrl.clear();
        //TODO удаляю префикс https:// , так как ввод с клавы без него и метод принимает без него
        hashSetForClear.forEach(s -> {
            String sub = s.substring(s.indexOf(site));
            mainListUrl.add(sub);
        });
        for (int i = 0; i < mainListUrl.size(); i++) {//удаляем главную страницу что бы избежать цикличности
            String search = site + "/";
            if (mainListUrl.get(i).equals(search)) {
                mainListUrl.remove(i);
            }
        }
        for (int i = 0; i < mainListUrl.size(); i++) {//удаляем все ссылки где подклчюение через порт
            if (mainListUrl.get(i).contains(":")) {
                mainListUrl.remove(i);
            }
        }
        saveMainLink(mainListUrl, "only_need");//на этом этапе отселилось все кроме ссылок
        //makeTreeMapMainSite(mainListUrl);//засунул все в мап дял построения дерева сайта
        return mainListUrl;
    }

    // делаю тримап с главной страницей
    public static void createFileAndStart() throws IOException {//need help создаю файл запускаю парсинг
        Files.createFile(Path.of(myLog + "/newListSite.txt"));
        Files.createFile(Path.of(myLog + "/mapSite.txt"));
        //makeMapSiteFromStringToMap(site);
        newMethod(site);
        System.out.println("Done!");
    }

    // метод получает список и проходится по нему и все складывает в массив массивов
    public static TreeMap<String, TreeMap> makeMapSiteFromStringToMap(String in) throws IOException {//need help парсинг
        System.out.println("запущен метод " + count + "-" + in);
        Files.writeString(Path.of(myLog + "/listSite.txt"), in + "\n", StandardOpenOption.APPEND);
        if (count.get() == 1) {
            System.out.println("+");
            Files.writeString(Path.of(myLog + "/mapSite.txt"), in + "\n", StandardOpenOption.APPEND);
        }
        System.out.println("-");
        Files.writeString(Path.of(myLog + "/mapSite.txt"), "\t" + in + "\n", StandardOpenOption.APPEND);
        count.incrementAndGet();
        //переделываем в лист для поиска дубликатов
        ArrayList<String> listFromSet = new ArrayList<>(historySiteLinks);
        if (!listFromSet.contains(in)) {
            //set что бы небыло задвоений
            historySiteLinks.add(in);

            //todo здесть нужна проверка на корректность ввдененого адреса сайта
            treeMapSite = new TreeMap<>();
            try {
                ArrayList<String> allLinkInMain = getAllLinkInMain(in);
                ArrayList<String> tryLink = checkDomain(allLinkInMain);
                //TODO берем список всех сылок которые уже обрабатывали historySiteLinks
                // и проверяем с полученным списком tryLink,если такие есть удаляем,что бы избежать цикличности
                for (String s : listFromSet) { //проходимся по каждому элементу списка обработанных ссылок
                    for (int j = 0; j < tryLink.size(); j++) {//проходимся по каждому элементу списка новых полученных ссылок
                        tryLink.remove(s);//удаляем из полученного списка все ссылки которые уже были
                    }
                }
                if (tryLink.size() < 1 | tryLink == null) {
                    System.out.println("Переданный лист пуст");
                } else if (tryLink.size() > 0) {
                    //historySiteLinks.addAll(tryLink);//полуяенный список новых ссылок которые мы не проверяли добавляем в сет архивный.
                    for (int i = 0; i < tryLink.size(); i++) {
                        String keyMapRootLink = tryLink.get(i);
                        TreeMap<String, TreeMap> valueArrayListLinks = new TreeMap<>();
                        if (!(keyMapRootLink.equals(in))) {
                            Files.writeString(Path.of(myLog + "/mapSite.txt"), "" + keyMapRootLink + "\n", StandardOpenOption.APPEND);
                            treeMapSite.put(keyMapRootLink, valueArrayListLinks);
                            makeMapSiteFromStringToMap(keyMapRootLink);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            treeMapSite.forEach((k, v) -> {
                System.out.println(k + "\n" + v.toString());
            });
        }
        return treeMapSite;
    }

    public static ArrayList<String> newMethod(String in) throws IOException {//новый метод
        count.incrementAndGet();
        int indent = 0;
        String st = "ex";
        // String stex=st.indent(4);
        System.out.println("запущен метод " + count + "-" + in);//для мониторинга в консоли
        Files.writeString(Path.of(myLog + "/newListSite.txt"), in + "\n", StandardOpenOption.APPEND);
        Files.writeString(Path.of(myLog + "/mapSite.txt"), in + "\n", StandardOpenOption.APPEND);
        if (!(historySiteLinks.contains(in))) {//проверяем,парсили ли мы эту страницу
            ArrayList<String> tryLinks = checkDomain(getAllLinkInMain(in));//получили лист для дальнейшего парсинга
            historySiteLinks.add(in);//добавили в список проверенных,так как только что это сделали
            if (tryLinks.size() > 0) {//проверяем,есть ли ссылки для парсинга на этой странице
                tryLinks.forEach(s -> {
                    if (!(historySiteLinks.contains(s))) {//проверяем,есть ли строка из нового списка в истории
                        try {
                            // Files.writeString(Path.of(myLog + "/mapSite.txt"), (s + "\n"), StandardOpenOption.APPEND);
                            newMethod(s);//парсим
                            historySiteLinks.add(s);//добавляем в историю
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
        ArrayList<String> answer = new ArrayList<>(historySiteLinks);
        return answer;
    }

}
