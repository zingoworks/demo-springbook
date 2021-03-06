package io.zingoworks.demospringbook.user.dao;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@NoArgsConstructor
public class CountingConnectionMaker implements ConnectionMaker {
	
	int counter = 0;
	private ConnectionMaker realConnectionMaker;
	
	public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}
	
	public void setRealConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}
	
	@Override
	public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
		this.counter++;
		return realConnectionMaker.makeNewConnection();
	}
	
	public int getCounter() {
		return this.counter;
	}
}
