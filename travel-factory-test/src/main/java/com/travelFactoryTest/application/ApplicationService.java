package com.travelFactoryTest.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
	
	@Autowired
	ApplicationRepository applicationRepository;
	
	public List<Application> getAllApplications() {
		List<Application> applications = new ArrayList<>();
		applicationRepository.findAll().forEach(applications::add);
		return applications;
	}
	
	public Application getApplication(long appId) {
		return applicationRepository.findById(appId).get();
	}

	public Application saveApplication(Application app) {
		return applicationRepository.save(app);
	}
}
