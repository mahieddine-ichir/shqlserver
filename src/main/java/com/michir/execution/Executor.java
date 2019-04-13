package com.michir.execution;

public interface Executor {
	
	Boolean supported(String command);

	void run(String sql) throws Exception;

}
