// path: repsy-repo/repo-app/src/main/java/com/example/repsy/model/PackageId.java
package com.example.repsy.model;

import java.io.Serializable;

public class PackageId implements Serializable {
    private String name;
    private String version;

    public PackageId() {}

    public PackageId(String name, String version) {
        this.name = name;
        this.version = version;
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
}
