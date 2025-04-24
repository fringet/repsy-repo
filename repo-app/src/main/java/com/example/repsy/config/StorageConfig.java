// path: repsy-repo/repo-app/src/main/java/com/example/repsy/config/StorageConfig.java
package com.example.repsy.config;

import com.example.storage.StorageService;
import com.example.storage.fs.FileSystemStorageService;
import com.example.storage.minio.MinioStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

@Configuration
public class StorageConfig {

    @Value("${storageStrategy:file-system}")
    private String strategy;

    @Value("${storage.fs.root:uploads}")
    private String fsRoot;

    @Value("${storage.minio.endpoint:http://localhost:9000}")
    private String endpoint;
    @Value("${storage.minio.accessKey:minio}")
    private String accessKey;
    @Value("${storage.minio.secretKey:minio123}")
    private String secretKey;
    @Value("${storage.minio.bucket:repsy}")
    private String bucket;

    @Bean
    public StorageService storageService() throws Exception {
        if ("object-storage".equalsIgnoreCase(strategy)) {
            return new MinioStorageService(endpoint, accessKey, secretKey, bucket);
        } else {
            return new FileSystemStorageService(Paths.get(fsRoot));
        }
    }
}
