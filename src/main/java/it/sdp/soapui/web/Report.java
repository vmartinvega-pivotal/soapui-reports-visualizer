package it.sdp.soapui.web;

import java.io.Serializable;

public class Report implements Serializable,  Comparable<Report> {

    private static final long serialVersionUID = 1L;

    private String url;
    private String groupId;
    private String artifactId;
    private String environment;
    private int number;
    private String date;
    private String version;
    private boolean successful = true;
    private boolean fake = false;
    
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	// Version comparation
	public int compareTo(Report o) {
		int result = 0;
		if (getVersion() != null) {
			if (o.getVersion() != null) {
				int rep1 = new Integer(getVersion().replace(".", ""));
				int rep2 = new Integer(o.getVersion().replace(".", ""));
				if(rep1 < rep2) {
					result = 1;
				} else if(rep1 > rep2) {
					result = -1;
				}else {
					result = 0;
				}
			}else {
				result = 1;
			}
			
		}else {
			result = -1;
		}
		
		return result;
	}
	public boolean isFake() {
		return fake;
	}
	public void setFake(boolean fake) {
		this.fake = fake;
	}
}