package com.example.demo.file.controller;

import com.example.demo.file.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
  private final FileService fileService;

  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping(path = "/upload", consumes = "multipart/form-data")
  public String uploadFile(@RequestParam(name = "file") MultipartFile file) {
    fileService.uploadFile(file);
    return "File uploaded";
  }

  @GetMapping(path = "/download/{id}")
  public ResponseEntity<Resource> downloadFile(@PathVariable int id) throws Exception {
    return fileService.downloadFile(id);
  }
}
