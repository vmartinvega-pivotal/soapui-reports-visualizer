package it.sdp.soapui.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class ActionServlet extends HttpServlet {

    private static final long serialVersionUID = -5832176047021911038L;

    public static int PAGE_SIZE = 5;

    @EJB
    private ReportsBean reportsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String groupId = request.getParameter("groupid");
        String artifactId = request.getParameter("artifactid");

        String key = request.getParameter("key");
        String field = request.getParameter("field");

        int count = 0;

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            count = reportsBean.countAll();
            key = "";
            field = "";
        } else {
            count = reportsBean.count(field, key);
        }

        int page = 1;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }

        int pageCount = (count / PAGE_SIZE);
        if (pageCount == 0 || count % PAGE_SIZE != 0) {
            pageCount++;
        }

        if (page < 1) {
            page = 1;
        }

        if (page > pageCount) {
            page = pageCount;
        }

        int start = (page - 1) * PAGE_SIZE;
        List<Report> range;

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            range = reportsBean.findAll(start, PAGE_SIZE);
        } else {
            range = reportsBean.findRange(field, key, start, PAGE_SIZE);
        }

        int end = start + range.size();

        request.setAttribute("count", count);
        request.setAttribute("start", start + 1);
        request.setAttribute("end", end);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("reports", range);
        request.setAttribute("key", key);
        request.setAttribute("field", field);
        request.setAttribute("artifactid", artifactId);
        request.setAttribute("groupid", groupId);

        request.getRequestDispatcher("WEB-INF/reports.jsp").forward(request, response);
    }

}
