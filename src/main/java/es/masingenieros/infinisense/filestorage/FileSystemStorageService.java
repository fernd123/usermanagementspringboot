package es.masingenieros.infinisense.filestorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private Path rootLocation;
    private final String pathOriginal;

    public String getPathOriginal() {
		return pathOriginal;
	}

	@Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.pathOriginal = this.rootLocation.toString();
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file, String tenant, String entityName) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
            	
            	this.rootLocation = Paths.get(this.rootLocation.toString()+"/"+tenant+"/"+entityName);
            	
            	File directory = new File(this.rootLocation.toString());
                if (!directory.exists()){
                    directory.mkdirs();
                    // If you require it to make the entire directory path including parents,
                    // use directory.mkdirs(); here instead.
                }
                
            	Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            	this.rootLocation = Paths.get(this.pathOriginal);

            }
        }
        catch (IOException e) {
        	this.rootLocation = Paths.get(this.pathOriginal);
            throw new StorageException("Failed to store file " + filename, e);
        }

        return filename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename, String tenant, String entityName) {
        try {
        	this.rootLocation = Paths.get(this.rootLocation.toString()+"/"+tenant+"/"+entityName);
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
            	this.rootLocation = Paths.get(this.pathOriginal);
                return resource;
            }
            else {
            	this.rootLocation = Paths.get(this.pathOriginal);
                throw new FileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
        	this.rootLocation = Paths.get(this.pathOriginal);
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
