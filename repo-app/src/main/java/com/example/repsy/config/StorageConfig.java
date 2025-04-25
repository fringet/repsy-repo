package com.example.repsy.config;

import com.example.storage.StorageService;
import com.example.storage.fs.FileSystemStorageService;
import com.example.storage.minio.MinioStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties(StorageProps.class)
public class StorageConfig {

    private final StorageProps props;

    public StorageConfig(StorageProps props) {
        this.props = props;
    }

    /**
     * File-system strategy bean, active when
     * storage.strategy=file-system (or unset).
     */
    @Bean
    @ConditionalOnProperty(
        name = "storage.strategy",
        havingValue = "file-system",
        matchIfMissing = true
    )
    public StorageService fileSystemStorage() throws Exception {
        // Convert root directory from String to Path
        return new FileSystemStorageService(Paths.get(props.getFs().getRoot()));
    }

    /**
     * MinIO strategy bean, active when
     * storage.strategy=object-storage
     */
    @Bean
    @ConditionalOnProperty(
        name = "storage.strategy",
        havingValue = "object-storage"
    )
    public StorageService minioStorage() throws Exception {
        StorageProps.Minio m = props.getMinio();
        return new MinioStorageService(
            m.getEndpoint(),
            m.getAccessKey(),
            m.getSecretKey(),
            m.getBucket()
        );
    }
}