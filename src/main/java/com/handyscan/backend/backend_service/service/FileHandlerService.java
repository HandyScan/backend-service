package com.handyscan.backend.backend_service.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileHandlerService {
    
    public InputStream readFile(String filename, String path);

    public boolean saveFile(InputStream inputStream, String fileName, String path);

}
