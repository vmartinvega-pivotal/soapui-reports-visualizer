package it.sdp.soapui.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import it.sdp.soapui.SoapUtilException;
import it.sdp.soapui.nexus.NexusUtils;

import java.util.Map;
import java.util.Vector;

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
    public String setup(Map<String, Object> model) throws SoapUtilException {
    	reportsBean.clean();
    	NexusUtils nexusUtils = new NexusUtils();
    	Vector<Report> reports;
    	
		//reports = nexusUtils.readAllReports("http://nexus.com", "collaudodevopsnexus", "collaudodevopsnexus1234");
		reports = nexusUtils.readAllReports();
		for (int nIndex = 0; nIndex < reports.size(); nIndex++) {
    		reportsBean.addReport(reports.get(nIndex));	
    	}
    	    	
        model.put("reports", reportsBean.getReports());

        return "setup";
    }
}
