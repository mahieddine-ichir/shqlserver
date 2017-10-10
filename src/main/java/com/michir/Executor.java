package com.michir;

public interface Executor {
	
	Boolean supported(String command);

	void run(String sql) throws Exception;

}
