package com.project.library.Library.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.library.Library.exception.ContextedRuntimeException;
import com.project.library.Library.model.DatabaseFile;
import com.project.library.Library.repository.DatabaseFileRepository;

@Service
public class DatabaseFileService {

    @Autowired
    private DatabaseFileRepository dbFileRepository;

    public DatabaseFile storeFile(MultipartFile file,Long custid) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new ContextedRuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes(),custid);

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new ContextedRuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DatabaseFile getFile(String fileName) {
        return dbFileRepository.findByfileName(fileName)
            .orElseThrow(() -> new ContextedRuntimeException("File not found with name " + fileName));
    }
}