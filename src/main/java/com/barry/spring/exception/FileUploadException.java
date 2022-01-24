package com.barry.spring.exception;
public class FileUploadException extends RuntimeException {

    public FileUploadException(String msg) {
        super(msg);
    }
}