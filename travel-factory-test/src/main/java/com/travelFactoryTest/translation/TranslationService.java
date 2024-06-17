package com.travelFactoryTest.translation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelFactoryTest.application.Application;
import com.travelFactoryTest.application.ApplicationService;

@Service
public class TranslationService {
	
	@Autowired
	private TranslationRepository translationRepository;

	@Autowired
	private ApplicationService applicationService;
	
    private static String jsonSavePath = System.getProperty("user.dir") + "/translator";
    
	public List<Translation> getAllAppTranslations(long appId) {
		return translationRepository.findByApplicationId(appId);
	};
	
	public Translation saveTranslation(Translation translation) {
		return translationRepository.save(translation);
	}
	
	 public byte[] getTranslationsAsExcel(long appId) throws IOException {
	        List<Translation> translations = this.getAllAppTranslations(appId);
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Translations");

	        Row titleRow = sheet.createRow(0);
	        titleRow.createCell(0).setCellValue("Key");
	        titleRow.createCell(1).setCellValue("Value");
	        
	        int rowNum = 1;
	        for (Translation translation : translations) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(translation.getKey());
	            row.createCell(1).setCellValue(translation.getValue());
	        }

	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        workbook.close();

	        return outputStream.toByteArray();
	    }

		public String deployTranslations(long appId, byte[] excelBytes) {
			Application currentApp = applicationService.getApplication(appId);

			   try {
		            // Convert byte array back to Excel file
		            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(excelBytes);
		            Workbook workbook = new XSSFWorkbook(byteArrayInputStream);

		            // Parse the Excel file and convert to JSON
		            Map<String, String> translations = parseExcelFile(workbook);

		            // Save JSON to the specified path
		            saveJsonToFile(translations, currentApp.getName());

		            currentApp.setLastDeployment(LocalDateTime.now());
		            applicationService.saveApplication(currentApp);
		            
		            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
		            return currentApp.getLastDeployment().format(formatter);
		        } catch (Exception e) {
		            return "Error: " + e.getMessage();
		        }

		}
		
		private Map<String, String> parseExcelFile(Workbook workbook) throws IOException {
	        Map<String, String> translations = new HashMap<>();
	        Sheet sheet = workbook.getSheetAt(0);

	        Iterator<Row> rows = sheet.iterator();
	        if (rows.hasNext()) rows.next() ; // skip title row
	        while (rows.hasNext()) {
	            Row currentRow = rows.next();
	            Cell keyCell = currentRow.getCell(0);
	            Cell valueCell = currentRow.getCell(1);

	            String key = keyCell.getStringCellValue();
	            String value = valueCell.getStringCellValue();
	            translations.put(key, value);
	        }
	        workbook.close();
	        return translations;
	    }

	    private void saveJsonToFile(Map<String, String> translations, String appName) throws IOException {
	        ObjectMapper objectMapper = new ObjectMapper();
	        File directory = new File(jsonSavePath);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }
	        File jsonFile = new File(directory, appName + ".json");
	        objectMapper.writeValue(jsonFile, translations);
	    }
		
		
}
