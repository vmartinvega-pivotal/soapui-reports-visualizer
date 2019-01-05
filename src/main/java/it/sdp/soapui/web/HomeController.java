package it.sdp.soapui.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

    private final ReportsBean reportsBean;

    public HomeController(ReportsBean reportsBean) {
        this.reportsBean = reportsBean;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
    	reportsBean.clean();
    	
    	Report report = new Report ();
    	report.setArtifactId("domiciliazioni");
    	report.setGroupId("it.sdp");
    	report.setEnvironment("coll");
    	report.setUrl("https://algo.com");
    	report.setNumber(1);
    	report.setDate("2018-01-23 12:54");
    	reportsBean.addReport(report);
    	reportsBean.addReport(report);
    	reportsBean.addReport(report);
    	reportsBean.addReport(report);
    	reportsBean.addReport(report);
    	reportsBean.addReport(report);
    	reportsBean.addReport(report);
    	reportsBean.addReport(report);
    	reportsBean.addReport(report);
    	reportsBean.addReport(report);
    	reportsBean.addReport(report);

        model.put("reports", reportsBean.getReports());

        return "setup";
    }
}
