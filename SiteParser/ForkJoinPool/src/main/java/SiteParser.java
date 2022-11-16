import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SiteParser extends RecursiveTask<String> {
    private static AtomicInteger count = new AtomicInteger(0);
    private String url;
    private static CopyOnWriteArrayList<String> historyUrl = new CopyOnWriteArrayList<>();

    protected SiteParser(String url) {
        this.url = url.trim();
    }

    @Override
    protected String compute() {
        //облегчаем себе жизнь сторонней библиотекой.
        String stringUtils = StringUtils.repeat("\t", url.lastIndexOf("/") != url.length() - 1 ? StringUtils
                .countMatches(url, "/") - 2 : StringUtils.countMatches(url, "/") - 3);
        StringBuffer sb = new StringBuffer(String.format("%s%s%s", stringUtils, url, System.lineSeparator()));
        List<SiteParser> taskList = new CopyOnWriteArrayList<>();//создаем лист задач
        Document document;
        Elements elements;
        try {
            Thread.sleep(150);//избегаем првоерки на бота/капчи
            document = Jsoup.connect(url).ignoreContentType(true).maxBodySize(0).get();
            elements = document.select("a[href]");
            for (Element element : elements) {
                String attributeUrl = element.absUrl("href");
                if (!attributeUrl.isEmpty() && attributeUrl.startsWith(url) && !historyUrl.contains(attributeUrl) && !attributeUrl
                        .contains("#")) {//отсеиваем
                    SiteParser siteParser = new SiteParser(attributeUrl);
                    siteParser.fork();//ответвляем задачу
                    taskList.add(siteParser);//отправляем в лист задач
                    historyUrl.add(attributeUrl);
                    count.incrementAndGet();
                    System.out.println("Порядковый номер-"+count+", ссылка- "+attributeUrl);//печать в консоль дял мониторинга парсинга
                }
            }
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getMessage());//печатаем если что не так
        }
        taskList.sort(Comparator.comparing((SiteParser o) -> o.url));//проходимся по сформированному листу задач
        int i = 0, allTasksSize = taskList.size();
        while (i < allTasksSize) {
            SiteParser link = taskList.get(i);
            sb.append(link.join());//собираем все задачи в кучу
            i++;
        }
        count.incrementAndGet();
        System.out.println("Порядковый номер-"+count+", ссылка- "+sb);//печать в консоль дял мониторинга парсинга
        return sb.toString();
    }
}