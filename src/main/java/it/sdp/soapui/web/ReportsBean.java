package it.sdp.soapui.web;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReportsBean {
	
	private ArrayList<Report> reports = new ArrayList<Report>();
	
	private ArrayList<Report> artifactsReports = new ArrayList<Report>();
	
	private Map<String, ArrayList<Report>> reportsByGroupIdArfifactId = new HashMap<String, ArrayList<Report>>(); 
	
	private boolean initialized = false;
	
	public synchronized boolean getInitialized() {
		return initialized;
	}
	
	public synchronized void setInitialized(boolean value) {
		this.initialized = value;
	}
    
    public void addReport(Report report) {
    	reports.add(report);
    }
    
    private List<Report> genericFindAllArtifacts(ArrayList<Report> reports, int firstResult, int maxResults) {
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
    
    public List<Report> findAllArtifacts(int firstResult, int maxResults, String regex) {
    	return genericFindAllArtifacts(getDistinctArtifacts(regex), firstResult, maxResults);
    }
        
    public List<Report> findAllArtifacts(int firstResult, int maxResults) {
    	return findAllArtifacts(firstResult, maxResults, null);
    }

    public List<Report> findAll(int firstResult, int maxResults) {
    	return genericFindAllArtifacts(reports, firstResult, maxResults);
    }
    
    private synchronized ArrayList<Report> getReporstByGroupIdArtifactId(String groupId, String artifactId, String environment){
    	//ArrayList<Report> result = reportsByGroupIdArfifactId.get(groupId + artifactId);
    	//if(result == null) {
     		ArrayList<Report> reportList = new ArrayList<Report>();
    		for (int nIndex = 0; nIndex < reports.size(); nIndex++) {
        		Report report = reports.get(nIndex);
        		if(report.getArtifactId().equals(artifactId) && report.getGroupId().equals(groupId)) {
        			if((environment == null) || (report.getEnvironment().contains(environment))) {
        				reportList.add(report);	
        			}
        		}
    		}
    		reportsByGroupIdArfifactId.put(groupId + artifactId, reportList);
    		//result = reportList;
    	//}
    	//Collections.sort(reportList);
    	//return result;
    	Collections.sort(reportList);
    	return reportList;
    }
    
    private synchronized ArrayList<Report> getReporstByGroupIdArtifactId(String groupId, String artifactId){
    	return getReporstByGroupIdArtifactId(groupId, artifactId, null);
    }
    
    private boolean match (String regex, String value) {
    	boolean result = true;
    	
    	if(regex != null) {
    		if(!value.contains(regex)) {
    			return false;
    		}
    	}
    	return result;
    }
    
    private synchronized ArrayList<Report> getDistinctArtifacts(String regexArtifact) {

    	//if (artifactsReports.size() == 0) {
    	artifactsReports.clear();
    		Map<String, Report> artifacts = new HashMap<String, Report>();
    		for (int nIndex = 0; nIndex < reports.size(); nIndex++) {
        		Report report = reports.get(nIndex);
        		if (!artifacts.containsKey(report.getGroupId() + report.getArtifactId())) {
        			if (match (regexArtifact, report.getArtifactId())) {
        				Report artifactReport = new Report();
            			artifactReport.setArtifactId(report.getArtifactId());
            			artifactReport.setGroupId(report.getGroupId());
            			artifactReport.setNumber(1);
            			artifacts.put(report.getGroupId() + report.getArtifactId(), artifactReport);	
        			}
        		}else {
        			if (match (regexArtifact, report.getArtifactId())) {
        				Report auxArtifactReport = (Report) artifacts.get(report.getGroupId() + report.getArtifactId());
            			auxArtifactReport.setNumber(auxArtifactReport.getNumber()+1);
        			}
        		}
        	}	
    		artifactsReports = new ArrayList<Report>(artifacts.values());
    	//}
    	
    	return artifactsReports;
    }
    
    // Counts all distinct artifacts
    public int countAllArtifacts(String regex) {
    	return getDistinctArtifacts(regex).size();
    }
    
    public int countAll() {
        return reports.size();
    }

    public int count(String groupId, String artifactId) {
       return getReporstByGroupIdArtifactId(groupId, artifactId).size();
    }

    public List<Report> findRange(String groupId, String artifactId, int firstResult, int maxResults) {
    	return genericFindAllArtifacts(getReporstByGroupIdArtifactId(groupId, artifactId), firstResult, maxResults);
    }

    public void clean() {
    	reports.clear();
    	artifactsReports.clear();
    	reportsByGroupIdArfifactId.clear();
    }
}