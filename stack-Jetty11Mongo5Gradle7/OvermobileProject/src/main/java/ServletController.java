//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@WebServlet(urlPatterns = {"/main"})
public class ServletController extends HttpServlet {
    private static Logger logger = Logger.getRootLogger();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log("Method init");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        log("Method service");
        resp.getWriter().write("Method service start \n");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("Method GET");
        resp.getWriter().write("Method GET start \n");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String log4jConfPath = "src/main/resources/app.properties";
        PropertyConfigurator.configure(log4jConfPath);
        log("Method POST");

        int id = Integer.parseInt(req.getParameter("id"));
        int useId = Integer.parseInt(req.getParameter("useId"));
        int sum = Integer.parseInt(req.getParameter("sum"));
        logger.info("\nвытащили id---" + id + ",  вытащили useId---" + useId + ",  вытащили sum---" + sum);
        Storage storage = new Storage();
        logger.info("* запустили из сервлета");
        int respStatus = storage.addPay(id, useId, sum, System.currentTimeMillis());
        //resp.sendRedirect("/OvermobileProject/static/form.html");//можем перенаправлять заново на новый ввод
        System.out.println("статус от БД " + respStatus);
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"/OvermobileProject/static/form.html\">go to page ADD pay</a>");
        out.println("<br/>");
        out.println("status " + respStatus);
        out.close();

    }

    @Override
    public void destroy() {
        log("Method destroy");
    }
}
