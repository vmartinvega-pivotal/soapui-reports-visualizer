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
	
	private static int FIELD_GROUP_ID = 0;
	private static int FIELD_ARTIFACT_ID = 1;
	private static int FIELD_DATE = 2;
	private static int FIELD_ENVIRONMENT = 3;
	private static int FIELD_VERSION = 4;
	
	private static int FIELDS_WITH_VERSION = 5;
	private static int FIELDS_WITHOUT_VERSION = 4;
	
	private Report parseUrl(String url) {
		// GROUPID_ARTIFACTID_YYYYMMDDHHMM_ENVIRONMENT_VERSION.html
		
		Report report = new Report();
	
		String[] params = StringUtils.splitPreserveAllTokens(url, SEPARATOR);
		
		// If the url has version it measns the report was successfull (version is not stored)
		if (params.length == FIELDS_WITH_VERSION) {
			report.setSuccessful(true);
			report.setVersion(params[FIELD_VERSION]);
		}else if (params.length == FIELDS_WITHOUT_VERSION) {
			report.setSuccessful(false);
		}else {
			return null;
		}
		report.setGroupId(params[FIELD_GROUP_ID]);
		report.setArtifactId(params[FIELD_ARTIFACT_ID]);
		report.setDate(params[FIELD_DATE]);
		report.setEnvironment(params[FIELD_ENVIRONMENT]);
		
		return report;
	}
	
	public Vector<Report> readAllReports() throws SoapUtilException {
		Vector<Report> result = new Vector<Report>();
		
		result.add(createReport("it.sdp", "domiciliazioni", "coll", "https://algo.com", "2018-01-23 12:54", "1.1.2"));
		result.add(createReport("it.sdp", "domiciliazioni", "coll", "https://algo.com", "2018-01-24 12:54", "1.1.2"));
		result.add(createReport("it.sdp", "domiciliazioni2", "coll", "https://algo.com", "2018-01-25 12:54", "1.1.2"));
    	
    	return result;
	}
	
	private Report createReport(String groupId, String artifactId, String env, String url, String date, String version) {
		Report report = new Report();
		
		report.setArtifactId(artifactId);
    	report.setGroupId(groupId);
    	report.setEnvironment(env);
    	report.setUrl(url);
    	report.setNumber(1);
    	report.setDate(date);
    	report.setVersion(version);
    	
		return report;
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
        			if (item.getDownloadUrl().trim().contains("FinalReports/html/")) {
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
