package it.sdp.soapui.nexus;

import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sdp.soapui.SoapUtilException;
import it.sdp.soapui.http.GetTest;
import it.sdp.soapui.http.HttpTestCaseHeadersImpl;
import it.sdp.soapui.utils.HTTPUtils;
import it.sdp.soapui.web.Report;

public class NexusUtils {
	
	private static String SEPARATOR = "_";
	
	private static String FINAL_REPORTS = "FinalReports/html/report_";
	
	private static String NEXUS_URL = "https://nexus-sdp.telecomitalia.local/nexus/repository/site/";
	
	private String parseDate(String date) {
		String result = null;
		
		if(date.length() == 12) {
			String year = date.substring(0, 4);
			String month = date.substring(4, 6);
			String day = date.substring(6, 8);
			String hour = date.substring(8 ,10);
			String minute = date.substring(10);
			
			result = year + "-" + month + "-" + day + " " + hour + ":" + minute;
		}
		
		return result;
	}
	
	private Report parseUrl(String url) {
		// FinalReports/html/GROUPID/ARTIFACTID/report_YYYYMMDDHHMM_ENVIRONMENT_VERSION.html
		//https://nexus-sdp.telecomitalia.local/nexus/repository/site/com.tim.sdp/domiciliazioni-id55/FinalReports/html/report_201901072210_coll_1.2.2.html
		//https://nexus-sdp.telecomitalia.local/nexus/repository/site/com.tim.sdp/domiciliazioni-id55/FinalReports/html/report_coll.html
		
		Report report = null;
    	if(url.contains(FINAL_REPORTS)) {
    		String aux = url.substring(url.indexOf(NEXUS_URL) + NEXUS_URL.length());
    		String[] params = StringUtils.splitPreserveAllTokens(aux, "/");
    		if (params.length == 5) {
    			String groupId = params[0];
    			String artifactId = params[1];
    			params = StringUtils.splitPreserveAllTokens(params[4], SEPARATOR);
    			if ((params.length == 4) || (params.length == 2)){
    				// This is a error report
    				String date = null;
    				if(params.length == 4) {
    					date = parseDate(params[1]);
        				if(date == null) {
        					return report;
        				}	
    				}
    				
    				String environment = null;
    				if(params .length == 4) {
    					environment = params[2];
    				// It is a error report
    				}else {
    					environment = params[1];
    					if(environment.contains(".html")) {
    						environment = environment.substring(0,  ".html".length() - 1);
    					}else {
    						// Skip no acaba en .html
    						return report;
    					}
    				}
    				
    				String version = null;
    				if (params.length == 4) {
    					version = params[3];
    					if (version.contains(".html")) {
    						version = version.substring(0,  ".html".length());
    					}else {
    						// Skip no acaba en .html
    						return report;
    					}
    				}
    				report = new Report();
    				report.setGroupId(groupId);
    				report.setArtifactId(artifactId);
    				report.setDate(date);
    				report.setEnvironment(environment);
    				report.setUrl(url);
    				if(version != null) {
    					report.setVersion(version);	
    					report.setSuccessful(true);
    				}else {
    					report.setSuccessful(false);
    				}
    				
    			}else {
    				//Skip el formato debe ser YYYYMMDDHHMM_ENVIRONMENT_VERSION.html
    			}
    		}else {
    			//Skip (el formato debe ser FinalReports/html/GROUPID/ARTIFACTID/YYYYMMDDHHMM_ENVIRONMENT_VERSION.html
    		}
    	}else {
    		// Skip
    	}
		
		return report;
	}
	
	public Vector<Report> readAllReports() throws SoapUtilException {
		Vector<Report> result = new Vector<Report>();
		
		result.add(parseUrl("https://nexus-sdp.telecomitalia.local/nexus/repository/site/com.tim.sdp/domiciliazioni-id55/FinalReports/html/report_coll.html"));
		result.add(parseUrl("https://nexus-sdp.telecomitalia.local/nexus/repository/site/com.tim.sdp/domiciliazioni-id55/FinalReports/html/report_201901072210_coll_1.2.2.html"));
    	
    	return result;
	}
			
	public Vector<Report> readAllReports(String url, String username, String password) throws SoapUtilException {
		Vector<Report> result = new Vector<Report>();
		GetTest getTest = new GetTest("", url, new HttpTestCaseHeadersImpl(), "", HttpStatus.OK);
    	getTest.setUsername(username);
    	getTest.setPassword(password);
    	HTTPUtils httpUtils = new HTTPUtils(true);
    	String response = httpUtils.sendGetAndCheckHttpStatus(getTest);
    	ObjectMapper objectMapper = new ObjectMapper();
    	try {
    		Assets assets = objectMapper.readValue(response.getBytes(), Assets.class);
        	while (true) {
        		for (int index=0; index< assets.getItems().size(); index++){
        			Item item = assets.getItems().get(index);
        			if (item.getDownloadUrl().trim().contains(FINAL_REPORTS)) {
        				Report report = parseUrl(item.getDownloadUrl());
        				if(report != null) {
        					result.add(report);	
        				}
        			}
        		}
        		if (assets.getContinuationToken() == null) {
        			break;
        		}
        		getTest.setUrl(url + "&continuationToken=" + assets.getContinuationToken());
        		response = httpUtils.sendGetAndCheckHttpStatus(getTest);
        		assets = objectMapper.readValue(response.getBytes(), Assets.class);
        	}	
    	}catch (Exception e) {
        	throw new SoapUtilException(e);
    	}
    	
    	return result;
	}
}
