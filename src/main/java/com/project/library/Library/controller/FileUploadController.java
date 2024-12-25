package com.project.library.Library.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.library.Library.exception.ResourceNotFoundException;
import com.project.library.Library.model.DatabaseFile;
import com.project.library.Library.repository.DatabaseFileRepository;
import com.project.library.Library.service.DatabaseFileService;
import com.project.library.Library.utils.Response;

import jakarta.servlet.http.HttpServletRequest;
@CrossOrigin("*")
@RestController
public class FileUploadController {

    @Autowired
    private DatabaseFileService fileStorageService;
    @Autowired
    private DatabaseFileRepository databaseFileRepository;
    @PostMapping("/uploadFile/{custid}")
    public Response uploadFile(@RequestParam("file") MultipartFile file,@PathVariable Long custid) {
        DatabaseFile fileName = fileStorageService.storeFile(file,custid);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/downloadFile/")
            .path(fileName.getFileName())
            .toUriString();
        return new Response(fileName.getFileName(), fileDownloadUri,
            file.getContentType(), file.getSize());
    }
    @GetMapping
    public List<DatabaseFile> getBookName(){
        return databaseFileRepository.findAll();
    }
    @GetMapping("/{custid}")
    public String getBookById(@PathVariable long custid){
        String bookname = databaseFileRepository.un(custid)
                            .orElseThrow(() -> new ResourceNotFoundException("error"));
        return bookname;
    }
    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        DatabaseFile databaseFile = fileStorageService.getFile(fileName);

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(databaseFile.getFileType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.getFileName() + "\"")
            .body(new ByteArrayResource(databaseFile.getData()));
    }
}
