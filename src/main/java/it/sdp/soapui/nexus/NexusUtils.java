package it.sdp.soapui.nexus;

import java.util.Vector;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sdp.soapui.SoapUtilException;
import it.sdp.soapui.http.GetTest;
import it.sdp.soapui.http.HttpTestCaseHeadersImpl;
import it.sdp.soapui.utils.HTTPUtils;
import it.sdp.soapui.web.Report;

public class NexusUtils {
			
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
        				Report report = new Report();
        				report.setUrl(item.getDownloadUrl());
        				result.add(report);
        				System.out.println(item.getDownloadUrl());
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
