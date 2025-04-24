// path: repsy-repo/storage-object-storage/src/main/java/com/example/storage/minio/MinioStorageService.java
package com.example.storage.minio;

import com.example.storage.StorageService;
import io.minio.*;

import java.io.IOException;
import java.io.InputStream;

public class MinioStorageService implements StorageService {

    private final MinioClient client;
    private final String bucket;

    public MinioStorageService(String endpoint,
                               String accessKey,
                               String secretKey,
                               String bucket) throws Exception {
        this.client = MinioClient.builder()
                                 .endpoint(endpoint)
                                 .credentials(accessKey, secretKey)
                                 .build();
        this.bucket = bucket;
        createBucketIfMissing();
    }

    private void createBucketIfMissing() throws Exception {
        boolean exists = client.bucketExists(
                BucketExistsArgs.builder().bucket(bucket).build());
        if (!exists) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    @Override
    public void save(String pkg, String version,
                     String fileName, InputStream content) throws IOException {
        try {
            client.putObject(
                PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(pkg + "/" + version + "/" + fileName)
                    .stream(content, -1, 10 * 1024 * 1024) // 10 MB parça
                    .build());
        } catch (Exception e) {
            throw new IOException("MinIO save error", e);
        }
    }

    @Override
    public InputStream load(String pkg, String version,
                            String fileName) throws IOException {
        try {
            return client.getObject(
                GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(pkg + "/" + version + "/" + fileName)
                    .build());
        } catch (Exception e) {
            throw new IOException("MinIO load error", e);
        }
    }
}
