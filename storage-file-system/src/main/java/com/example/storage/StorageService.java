// storage-file-system/src/main/java/com/example/storage/StorageService.java
package com.example.storage;

import java.io.IOException;
import java.io.InputStream;

public interface StorageService {
    /**
     * @param pkg       Paket adı
     * @param version   Sürüm
     * @param fileName  Kaydedilecek dosya adı (ör. "package.rep", "meta.json")
     * @param content   Dosyanın içeriği
     */
    void save(String pkg, String version, String fileName, InputStream content) throws IOException;

    /**
     * @param pkg       Paket adı
     * @param version   Sürüm
     * @param fileName  Okunacak dosya adı
     * @return          Dosya içeriğini okuyan InputStream
     */
    InputStream load(String pkg, String version, String fileName) throws IOException;
}
