package org.yeinp.animate.animate_backend.notice.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yeinp.animate.animate_backend.miss.dto.S3MissFileDto;
import org.yeinp.animate.animate_backend.notice.dto.S3NoticeFileDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsNoticeServiceImpl implements AwsNoticeService{

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket3}")
    private String bucket;


    @Override
    public List<S3NoticeFileDto> uploadFileNotice(List<MultipartFile> multipartFiles) {

        List<S3NoticeFileDto> s3NoticeFiles = new ArrayList<>();
        int count = 1;

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFileName = multipartFile.getOriginalFilename();

            String renamedFileName = getRenamedFileName(originalFileName, count);
            count++;
            String fileUrl = "";

            try {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(multipartFile.getContentType());
                metadata.setContentLength(multipartFile.getSize());
                amazonS3Client.putObject(bucket, renamedFileName, multipartFile.getInputStream(), metadata);
                fileUrl = amazonS3Client.getUrl(bucket, renamedFileName).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            s3NoticeFiles.add(S3NoticeFileDto.builder().originFileName(originalFileName).renamedFileName(renamedFileName).fileUrl(fileUrl).build());
        }
        return s3NoticeFiles;
    }

    @Override
    public Object deleteFiles(String fileName) {
        String result = "delete file";

        boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, fileName);
        if(isObjectExist){
            amazonS3Client.deleteObject(bucket, fileName);
        }else {
            result = "file not found";
        }
        return result;
    }


    @Override
    public String getRenamedFileName(String originalFileName, int count) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);

        String ext = originalFileName.substring(originalFileName.indexOf(".") + 1);
        String renamedFileName = originalFileName.replaceAll(originalFileName, str) + "_" + String.format("%02d",count) + "."+ext;

        return renamedFileName;
    }



}


