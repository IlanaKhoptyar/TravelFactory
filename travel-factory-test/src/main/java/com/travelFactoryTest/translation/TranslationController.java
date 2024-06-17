package com.travelFactoryTest.translation;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelFactoryTest.application.Application;

@RestController
@RequestMapping("/api/apps/{appId}")
public class TranslationController {
	
	@Autowired
	private TranslationService translationService;
	
	@GetMapping("/trans")
	public List<Translation> getAllTranslationsByApp(@PathVariable long appId) {
		return translationService.getAllAppTranslations(appId);
	}
	
	@PostMapping("/trans")
	public Translation saveTranslation(@PathVariable long appId, @RequestBody Translation translation) {
		if (translation.getKey() == null || translation.getKey().isEmpty())
			return null;
		
		Application app = new Application();
		app.setId(appId);
		translation.setApplication(app);
		return translationService.saveTranslation(translation);
	}
	
	@GetMapping("/download")
	public byte[] downloadTranslationsByApp(@PathVariable long appId) {
		try {
			return translationService.getTranslationsAsExcel(appId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping("/deploy")
	public String deployTranslationsByApp(@PathVariable long appId,  @RequestBody byte[] fileBytes) {
		if (fileBytes.length > 0) {
			return translationService.deployTranslations(appId, fileBytes);
		}
		return null;
	}
	

}
