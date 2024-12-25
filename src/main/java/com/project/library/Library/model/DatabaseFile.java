package com.project.library.Library.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class DatabaseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private String fileType;
    @Lob
    private byte[] data;
    private Long empid;
    public DatabaseFile(String fileName, String fileType, byte[] data,Long empid) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.empid = empid;
    }
}