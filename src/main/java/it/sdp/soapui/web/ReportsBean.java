package it.sdp.soapui.web;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportsBean {
	
	private ArrayList<Report> reports = new ArrayList<Report>();
    
    public void addReport(Report report) {
    	reports.add(report);
    }

    public List<Report> getReports() {
    	return reports;
    }

    public List<Report> findAll(int firstResult, int maxResults) {
    	ArrayList<Report> result = new ArrayList<Report>();
    	int end = firstResult + maxResults;
    	if (end > reports.size()) {
    		end = reports.size();
    	}
    	for (int index = firstResult; index < end; index++) {
    		result.add(reports.get(index));
    	}
    	return result;
    }

    public int countAll() {
        return reports.size();
    }

    public int count(String field, String searchTerm) {
       return 0;
    }

    public List<Report> findRange(String field, String searchTerm, int firstResult, int maxResults) {
    	return new ArrayList<Report>();
    }

    public void clean() {
    	reports.clear();
    }
}