package com.zensar.service.library.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.service.library.model.ServiceLibraryDto;
import com.zensar.service.library.service.LibraryService;

@RestController
@CrossOrigin(origins = "*")
public class ServiceLibraryController {

	@Autowired
	private LibraryService libraryService;
	private static final Logger log = LoggerFactory.getLogger(ServiceLibraryController.class);

	@GetMapping(value = "/library/{id}",name = "libraryById",path = "/library/{id}")
	public ResponseEntity<ServiceLibraryDto> getServiceLibraryById(@PathVariable Long id) {
		log.info("Get service library by id: " + id);
		ServiceLibraryDto dto = libraryService.getServiceLibraryById(id);
		return new ResponseEntity<ServiceLibraryDto>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/service/{name}")
	public ResponseEntity<List<ServiceLibraryDto>> getServiceLibraryByName(@PathVariable String name) {
		log.info("Get service library by name: " + name);
		List<ServiceLibraryDto> dtos = libraryService.getServiceByName(name);
		return new ResponseEntity<List<ServiceLibraryDto>>(dtos, HttpStatus.OK);
	}

	@GetMapping("/library")
	public ResponseEntity<List<ServiceLibraryDto>> getAllServiceLibrary() {
		log.info("Get full service library::");
		List<ServiceLibraryDto> dto = libraryService.getServiceLibrary();
		return new ResponseEntity<List<ServiceLibraryDto>>(dto, HttpStatus.OK);
	}

	@PostMapping("/library")
	public ResponseEntity<ServiceLibraryDto> serviceLibrary(@RequestBody ServiceLibraryDto dto) throws IOException {
		log.info("Save service library::" + dto.getServiceName());
		// dto.setLogoImage(compressBytes(dto.getLogoImage().length));
		dto = libraryService.saveServiceLibrary(dto);
		// dto.setLogoImage(decompressBytes(dto.getLogoImage()));
		log.info("Saved service library id::" + dto.getServiceId());
		return new ResponseEntity<ServiceLibraryDto>(dto, HttpStatus.OK);
	}

	@PutMapping("/library")
	public ResponseEntity<ServiceLibraryDto> updateService(@RequestBody ServiceLibraryDto dto) {
		log.info("Updating Service..");
		dto = libraryService.updateService(dto);
		log.info("Updating Service finished..");
		return new ResponseEntity<ServiceLibraryDto>(dto, HttpStatus.OK);
	}

//	@PostMapping(value = "/upload",headers = "content-type=multipart/*")
//	public ResponseEntity<Boolean> uploadLogo(@RequestParam("image") MultipartFile file) {
//		ServiceLibraryDto dto
//		return null;
//	}

	// compress the image bytes before storing it in the database
//	public static byte[] compressBytes(byte[] data) {
//		Deflater deflater = new Deflater();
//		deflater.setInput(data);
//		deflater.finish();
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//		byte[] buffer = new byte[1024];
//		while (!deflater.finished()) {
//			int count = deflater.deflate(buffer);
//			outputStream.write(buffer, 0, count);
//		}
//		try {
//			outputStream.close();
//		} catch (IOException e) {
//		}
//		log.info("Compressed Image Byte Size - " + outputStream.toByteArray().length);
//		return outputStream.toByteArray();
//	}
//
//	// uncompress the image bytes before returning it to the angular application
//	public static byte[] decompressBytes(byte[] data) {
//		Inflater inflater = new Inflater();
//		inflater.setInput(data);
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//		byte[] buffer = new byte[1024];
//		try {
//			while (!inflater.finished()) {
//				int count = inflater.inflate(buffer);
//				outputStream.write(buffer, 0, count);
//			}
//			outputStream.close();
//		} catch (IOException ioe) {
//		} catch (DataFormatException e) {
//		}
//		log.info("Decompressed Image Byte Size - " + outputStream.toByteArray().length);
//		return outputStream.toByteArray();
//	}
}
