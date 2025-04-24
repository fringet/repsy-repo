// path: repsy-repo/repo-app/src/main/java/com/example/repsy/model/PackageEntity.java
package com.example.repsy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "packages")
@IdClass(PackageId.class)
public class PackageEntity {

    @Id
    private String name;

    @Id
    private String version;

    private String author;

    @Lob
    @Column(columnDefinition = "jsonb")
    private String metaJson;

    public PackageEntity() {}

    public PackageEntity(String name, String version, String author, String metaJson) {
        this.name = name;
        this.version = version;
        this.author = author;
        this.metaJson = metaJson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMetaJson() {
        return metaJson;
    }

    public void setMetaJson(String metaJson) {
        this.metaJson = metaJson;
    }
}
