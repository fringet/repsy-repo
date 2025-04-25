// path: repo-app/src/main/java/com/example/repsy/config/StorageProps.java
package com.example.repsy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public class StorageProps {

    /** “file-system” or “object-storage” */
    private String strategy = "file-system";

    private final Fs fs = new Fs();
    private final Minio minio = new Minio();

    public String getStrategy() {
        return strategy;
    }
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Fs getFs() {
        return fs;
    }

    public Minio getMinio() {
        return minio;
    }

    public static class Fs {
        /** root directory for filesystem storage */
        private String root = "uploads";

        public String getRoot() {
            return root;
        }
        public void setRoot(String root) {
            this.root = root;
        }
    }

    public static class Minio {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String bucket;

        public String getEndpoint() {
            return endpoint;
        }
        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getAccessKey() {
            return accessKey;
        }
        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }
        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getBucket() {
            return bucket;
        }
        public void setBucket(String bucket) {
            this.bucket = bucket;
        }
    }
}
