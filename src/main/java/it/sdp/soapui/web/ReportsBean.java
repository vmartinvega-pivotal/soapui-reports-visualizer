/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    }
}