// path: repsy-repo/repo-app/src/main/java/com/example/repsy/controller/PackageController.java
package com.example.repsy.controller;

import com.example.storage.StorageService;
import com.example.repsy.model.PackageEntity;
import com.example.repsy.repo.PackageRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
public class PackageController {

    private final StorageService storage;
    private final PackageRepository repo;

    // Manuel constructor injection
    public PackageController(StorageService storage, PackageRepository repo) {
        this.storage = storage;
        this.repo = repo;
    }

    @PostMapping("/{packageName}/{version}")
    public ResponseEntity<?> upload(
            @PathVariable String packageName,
            @PathVariable String version,
            @RequestParam("package.rep") MultipartFile rep,
            @RequestParam("meta.json") MultipartFile meta) throws Exception {

        if (!rep.getOriginalFilename().endsWith(".rep")
                || !meta.getOriginalFilename().equals("meta.json")) {
            return ResponseEntity.badRequest().body("Invalid file names or types");
        }

        storage.save(packageName, version, "package.rep", rep.getInputStream());
        storage.save(packageName, version, "meta.json", meta.getInputStream());

        // Manuel entity oluşturma
        PackageEntity entity = new PackageEntity();
        entity.setName(packageName);
        entity.setVersion(version);
        entity.setAuthor("");
        entity.setMetaJson(new String(meta.getBytes()));
        repo.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{packageName}/{version}/{fileName}")
    public ResponseEntity<InputStreamResource> download(
            @PathVariable String packageName,
            @PathVariable String version,
            @PathVariable String fileName) throws Exception {

        InputStream in = storage.load(packageName, version, fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new InputStreamResource(in));
    }
}
