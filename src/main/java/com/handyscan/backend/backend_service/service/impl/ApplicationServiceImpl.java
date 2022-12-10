package com.handyscan.backend.backend_service.service.impl;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.handyscan.backend.backend_service.model.HandyScanRecord;
import com.handyscan.backend.backend_service.model.ObjectDetails;
import com.handyscan.backend.backend_service.model.ProcessingStatusEnum;
import com.handyscan.backend.backend_service.model.Response;
import com.handyscan.backend.backend_service.model.UserRecord;
import com.handyscan.backend.backend_service.repository.UserRecordRepository;
import com.handyscan.backend.backend_service.service.FileHandlerService;
import com.handyscan.backend.backend_service.service.KafkaService;

import lombok.extern.log4j.Log4j2;

import com.handyscan.backend.backend_service.service.ApplicationService;


@Service
@Log4j2
public class ApplicationServiceImpl implements ApplicationService {

    @Value(value = "${PROCESSING_BUCKET}")
    private String processingBucket;

    @Autowired
    FileHandlerService FileHandlerService;

    @Autowired
    KafkaService kafkaService;

    @Autowired
    UserRecordRepository userRecordRepository;

    @Override
    public Response storeFileInUserUploads(InputStream inputStream, String fileName, String username, String collection) {
        FileHandlerService.saveFile(inputStream, fileName, processingBucket);
        sendMessageToKafka(fileName, processingBucket, username, collection);
        updateDatabase(fileName, processingBucket, username, collection);
        return null;
    }

    private void sendMessageToKafka(String fileName, String bucket, String username, String collection){
        Map<String,String> message = new HashMap<>();
        message.put("fileName", fileName);
        message.put("bucket", processingBucket);
        message.put("userName", processingBucket);
        message.put("collection", processingBucket);
        kafkaService.sendMessageToOcr(message);
    }

    private void updateDatabase(String fileName, String bucket, String username, String collection){
        HandyScanRecord handyScanRecord = HandyScanRecord.builder()
            .status(ProcessingStatusEnum.OCR)
            .sourceFile(
                ObjectDetails.builder()
                    .bucket(bucket)
                    .fileName(fileName)
                    .build()
                )
            .build();
        Optional<UserRecord> optionalUserRecord = userRecordRepository.findItem(username, collection);
        UserRecord userRecord;
        if(optionalUserRecord.isPresent()){
            userRecord = optionalUserRecord.get();
            log.info("Found existing collection for the user", userRecord);
            userRecord.getFiles().put(fileName, handyScanRecord);
        }
        else{
            log.info("Creating new collection for the user");
            userRecord = UserRecord.builder()
            .id(UUID.randomUUID())
            .user(username)
            .collection(collection)
            .files(Collections.singletonMap(fileName, handyScanRecord))
            .build();
        }
        userRecordRepository.save(userRecord);
    }
    
}
