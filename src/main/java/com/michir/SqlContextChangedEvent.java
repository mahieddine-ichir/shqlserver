package com.michir;

public class SqlContextChangedEvent {

	private String context;

	public SqlContextChangedEvent(String context) {
		super();
		this.context = context;
	}

	/**
	 * @return the query
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param query the query to set
	 */
	public void setContext(String query) {
		this.context = query;
	}
	
	
}
