package it.sdp.soapui.web;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
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
    
    
    public List<Report> findAllArtifacts(int firstResult, int maxResults) {
    	return genericFindAllArtifacts(getDistinctArtifacts(), firstResult, maxResults);
    }

    public List<Report> findAll(int firstResult, int maxResults) {
    	return genericFindAllArtifacts(reports, firstResult, maxResults);
    }
    
    private synchronized ArrayList<Report> getReporstByGroupIdArtifactId(String groupId, String artifactId){
    	ArrayList<Report> result = reportsByGroupIdArfifactId.get(groupId + artifactId);
    	if(result == null) {
     		ArrayList<Report> reportList = new ArrayList<Report>();
    		for (int nIndex = 0; nIndex < reports.size(); nIndex++) {
        		Report report = reports.get(nIndex);
        		if(report.getArtifactId().equals(artifactId) && report.getGroupId().equals(groupId)) {
        			reportList.add(report);
        		}
    		}
    		reportsByGroupIdArfifactId.put(groupId + artifactId, reportList);
    		result = reportList;
    	}
    	return result;
    }
    
    private synchronized ArrayList<Report> getDistinctArtifacts() {

    	if (artifactsReports.size() == 0) {
    		Map<String, Report> artifacts = new HashMap<String, Report>();
    		for (int nIndex = 0; nIndex < reports.size(); nIndex++) {
        		Report report = reports.get(nIndex);
        		if (!artifacts.containsKey(report.getGroupId() + report.getArtifactId())) {
        			Report artifactReport = new Report();
        			artifactReport.setArtifactId(report.getArtifactId());
        			artifactReport.setGroupId(report.getGroupId());
        			artifactReport.setNumber(1);
        			artifacts.put(report.getGroupId() + report.getArtifactId(), artifactReport);
        		}else {
        			Report auxArtifactReport = (Report) artifacts.get(report.getGroupId() + report.getArtifactId());
        			auxArtifactReport.setNumber(auxArtifactReport.getNumber()+1);
        		}
        	}	
    		artifactsReports = new ArrayList<Report>(artifacts.values());
    	}
    	
    	return artifactsReports;
    }
    
    // Counts all distinct artifacts
    public int countAllArtifacts() {
    	return getDistinctArtifacts().size();
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