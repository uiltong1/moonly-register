package br.com.moonlyRegister.service;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.moonlyRegister.config.FileStorageConfig;
import br.com.moonlyRegister.exception.FileStorageException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {

		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Não foi possível criar o diretório para salvar o arquivo.", e);
		}
	}

	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Desculpa! O Nome do arquivo contém caracteres inválidos.");
			}

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (Exception e) {
			throw new FileStorageException(
					String.format("Não foi possível salvar o aqrquivo %s. Por favor, tente novamente", fileName));
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName);
			Resource resource = new UrlResource(filePath.toUri());

			if (!resource.exists()) {
				throw new FileNotFoundException(String.format("O arquivo %s não foi encontrado.", fileName));
			}

			return resource;

		} catch (Exception e) {
			throw new FileStorageException(String.format("O aqrquivo %s não foi encontrado.", fileName));
		}
	}

}
