package com.zensar.service.library.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "installationGuide")
@CrossOrigin(origins = "*")
public class InstallationGuideController {
	private static final Logger log = LoggerFactory.getLogger(InstallationGuideController.class);

	@GetMapping("/download/{fileName}")
	public ResponseEntity<?> downloadFileFromLocal(@PathVariable String fileName) throws IOException {
		String fileNameNew = fileName.toLowerCase() + "_installation_guide.txt";
		log.info("File to doanload {}",fileNameNew);
		File file = new ClassPathResource(fileNameNew).getFile();
		log.info("Installation guide file path:" + file.getAbsolutePath());
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(resource);
	}
}
