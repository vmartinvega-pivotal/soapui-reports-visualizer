package it.sdp.soapui.nexus;

import java.util.List;

public class Assets {
	
	private List<Item> items;
	private String continuationToken;
	
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public String getContinuationToken() {
		return continuationToken;
	}
	public void setContinuationToken(String continuationToken) {
		this.continuationToken = continuationToken;
	}
}
