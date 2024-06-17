package com.travelFactoryTest.translation;

import com.travelFactoryTest.application.Application;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Translation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true)
	private String key;
	private String value;
	
	@ManyToOne 
	private Application application;
	
	public Translation() {}

    public Translation(String key) {
        this.key = key;
    }
    
    public Translation(String key, String value, long applicationId) {
    	this.key = key;
    	this.value = value;
    	this.application = new Application(applicationId, "",null);
    }
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	 
}
