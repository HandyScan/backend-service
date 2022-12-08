package com.handyscan.backend.backend_service.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.handyscan.backend.backend_service.model.Response;
import com.handyscan.backend.backend_service.service.FileHandlerService;
import com.handyscan.backend.backend_service.service.KafkaService;
import com.handyscan.backend.backend_service.service.ApplicationService;


@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Value(value = "${PROCESSING_BUCKET}")
    private String processingBucket;

    @Autowired
    FileHandlerService FileHandlerService;

    @Autowired
    KafkaService kafkaService;

    @Override
    public Response storeFileInUserUploads(InputStream inputStream, String fileName) {
        FileHandlerService.saveFile(inputStream, fileName, processingBucket);
        Map<String,String> message = new HashMap<>();
        message.put("fileName", fileName);
        message.put("bucket", processingBucket);
        kafkaService.sendMessageToOcr(message);
        return null;
    }
    
}
