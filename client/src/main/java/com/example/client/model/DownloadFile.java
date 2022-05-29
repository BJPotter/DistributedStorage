package com.example.client.model;

import java.io.FileInputStream;


public class DownloadFile {
    private StorageFileInfo storageFileInfo;
    private FileInputStream fileInputStream;

    public StorageFileInfo getStorageFileInfo() {
        return storageFileInfo;
    }

    public void setStorageFileInfo(StorageFileInfo storageFileInfo) {
        this.storageFileInfo = storageFileInfo;
    }

    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }
}
