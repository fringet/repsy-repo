// storage-file-system/src/main/java/com/example/storage/fs/FileSystemStorageService.java
package com.example.storage.fs;

import com.example.storage.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class FileSystemStorageService implements StorageService {

    private final Path root;

    public FileSystemStorageService(Path root) throws IOException {
        this.root = root;
        Files.createDirectories(root);
    }

    @Override
    public void save(String pkg, String version, String fileName, InputStream content) throws IOException {
        Path dir = root.resolve(pkg).resolve(version);
        Files.createDirectories(dir);
        // Gelen içerği doğrudan dosyaya yaz (varsa üzerine yazar)
        Files.copy(content, dir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public InputStream load(String pkg, String version, String fileName) throws IOException {
        Path file = root.resolve(pkg).resolve(version).resolve(fileName);
        if (!Files.exists(file)) {
            throw new NoSuchFileException("Dosya bulunamadı: " + file.toString());
        }
        return Files.newInputStream(file, StandardOpenOption.READ);
    }
}
