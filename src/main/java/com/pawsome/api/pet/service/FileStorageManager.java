package com.pawsome.api.pet.service;

import org.springframework.web.multipart.MultipartFile;



public class FileStorageManager {

    public FileStorageManager.SavedFile  saveFile(MultipartFile file){

        return new FileStorageManager.SavedFile();

    }

    class SavedFile {

    }

}
