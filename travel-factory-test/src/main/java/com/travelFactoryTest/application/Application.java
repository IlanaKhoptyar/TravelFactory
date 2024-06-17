package com.travelFactoryTest.application;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private LocalDateTime lastDeployment;
	
	public Application() {};
	
	public Application(String name) {
		this.name = name;
	}

	public Application(long id, String name,LocalDateTime lastDeployment) {
		super();
		this.id = id;
		this.name = name;
		this.lastDeployment = lastDeployment;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getLastDeployment() {
		return lastDeployment;
	}

	public void setLastDeployment(LocalDateTime lastDeployment) {
		this.lastDeployment = lastDeployment;
	}
	
}
