// path: repsy-repo/repo-app/src/main/java/com/example/repsy/repo/PackageRepository.java
package com.example.repsy.repo;

import com.example.repsy.model.PackageEntity;
import com.example.repsy.model.PackageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<PackageEntity, PackageId> {
}
