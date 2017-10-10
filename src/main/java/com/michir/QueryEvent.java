package com.michir;

public class QueryEvent {

	private String query;

	public QueryEvent(String query) {
		super();
		this.query = query;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	
	
}
