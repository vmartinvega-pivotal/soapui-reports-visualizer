package it.sdp.soapui.web;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class ArtifactServlet extends HttpServlet {

    private static final long serialVersionUID = -5639176047021911038L;

    public static int PAGE_SIZE = 10;

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
    
    private void filterArtifacts(String regex) {
    	
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String field = request.getParameter("field");
        String key = request.getParameter("key");
        
        int count = reportsBean.countAllArtifacts(key);
        
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

        range = reportsBean.findAllArtifacts(start, PAGE_SIZE, key);

        int end = start + range.size();

        request.setAttribute("count", count);
        request.setAttribute("start", start + 1);
        request.setAttribute("end", end);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("reports", range);
        request.setAttribute("field", field);
        request.setAttribute("key", key);
        request.setAttribute("initialized", reportsBean.getInitialized());
        
        request.getRequestDispatcher("WEB-INF/artifacts.jsp").forward(request, response);
    }
}