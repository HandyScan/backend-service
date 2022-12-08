package com.handyscan.backend.backend_service.service;

import java.io.InputStream;

import com.handyscan.backend.backend_service.model.Response;

public interface ApplicationService {
    
    public Response storeFileInUserUploads(InputStream inputStream, String fileName);
}
