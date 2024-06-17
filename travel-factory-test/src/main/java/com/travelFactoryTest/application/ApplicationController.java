package com.travelFactoryTest.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;

	@GetMapping("/apps")
	public List<Application> getAllApps() {
		return applicationService.getAllApplications();
	}

	@PostMapping("/apps")
	public Application addApp(@RequestBody Application app) {
		return applicationService.saveApplication(app);
	}
}
