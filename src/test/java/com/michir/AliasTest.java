package com.michir;

import org.junit.Assert;
import org.junit.Test;

import com.michir.Alias;

public class AliasTest {
	
	@Test
	public void show_tables() {
		
		Alias show_tables = Alias.show_tables();
		Assert.assertTrue(show_tables.matches("show tables;"));
		Assert.assertTrue(show_tables.matches("show tables"));
		Assert.assertTrue(show_tables.matches("show Tables;"));
		Assert.assertTrue(show_tables.matches(" show   tables ; "));
	}
	
	@Test
	public void show_databases() {
		
		Alias show_databases = Alias.show_databases();
		Assert.assertTrue(show_databases.matches("show databases;"));
		Assert.assertTrue(show_databases.matches("show databases"));
		Assert.assertTrue(show_databases.matches("show datAbases;"));
		Assert.assertTrue(show_databases.matches(" show   databases ; "));
	}

}
