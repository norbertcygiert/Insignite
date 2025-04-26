package com.insignite;


import org.springframework.core.io.FileSystemResource;

public class MultipartFileRequest {
    private FileSystemResource file;

    public MultipartFileRequest(FileSystemResource file) {
        this.file = file;
    }

    public FileSystemResource getFile() {
        return file;
    }
}