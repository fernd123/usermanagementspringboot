package es.masingenieros.infinisense.filestorage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(MultipartFile file, String tenant, String entityName);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename, String tenant, String entityName);

    void deleteAll();

	String getPathOriginal();

}