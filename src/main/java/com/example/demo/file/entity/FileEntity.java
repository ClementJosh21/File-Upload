package com.example.demo.file.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "FILE_TABLE")
public class FileEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  private String fileType;
  private String fileName;

  @Column(name = "file", length = -1)
  @Type(type = "org.hibernate.type.ImageType")
  @Lob
  private byte[] file;
}
