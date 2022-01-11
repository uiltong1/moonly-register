package br.com.moonlyRegister.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.moonlyRegister.service.FileStorageService;
import br.com.moonlyRegister.vo.UploadFileResponseVO;

@RequestMapping("/api/v1/file")
@RestController
public class FileController {

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/upload")
	public UploadFileResponseVO upload(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/file/download/")
				.path(fileName).toUriString();

		return new UploadFileResponseVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/upload-multiples")
	public List<UploadFileResponseVO> uploadMultiples(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> upload(file)).collect(Collectors.toList());
	}

}
