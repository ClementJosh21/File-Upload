package com.example.demo.file.service;

import com.example.demo.file.entity.FileEntity;
import com.example.demo.file.repository.FileRepository;
import com.example.demo.file.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class FileService {

  public static final String MEDIA_TYPE_ZIP = "application/zip";
  public static final String HEADER = "attachment; filename=\"";
  private final FileUtil fileUtil;
  private final FileRepository fileRepository;

  public FileService(FileUtil fileUtil, FileRepository fileRepository) {
    this.fileUtil = fileUtil;
    this.fileRepository = fileRepository;
  }

  public void uploadFile(MultipartFile file) {
    try {
      File tempFile =
          fileUtil.getFileByBytes(
              file.getBytes(),
              FileUtils.getTempDirectoryPath() + File.separator + file.getOriginalFilename());
      FileEntity fileEntity =
          FileEntity.builder()
              .fileName(tempFile.getName())
              .fileType(tempFile.getName().split("\\.")[1])
              .file(fileUtil.fileToByteArray(tempFile))
              .build();
      fileRepository.save(fileEntity);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public ResponseEntity<Resource> downloadFile(int id) throws Exception {
    Optional<FileEntity> fileEntity = fileRepository.findById(id);
    if (fileEntity.isPresent()) {
      File tempFile =
          fileUtil.getFileByBytes(
              fileEntity.get().getFile(),
              FileUtils.getTempDirectoryPath() + File.separator + fileEntity.get().getFileName());
      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(MEDIA_TYPE_ZIP))
          .header(
              HttpHeaders.CONTENT_DISPOSITION,
              HEADER + fileEntity.get().getFileName() + fileEntity.get().getFileType())
          .body(new ByteArrayResource(fileUtil.fileToByteArray(tempFile)));
    } else {
      throw new Exception("Resource not found");
    }
  }
}
