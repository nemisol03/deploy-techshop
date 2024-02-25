package com.myshop.common;

import ch.qos.logback.core.net.server.Client;
import software.amazon.awssdk.core.ClientType;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class AmazonS3Util {
    private static final String BUCKET_NAME;
    static {
        BUCKET_NAME = "techshop-upload-files";
    }

    public static List<String> listFolder(String folderName) {



        S3Client client = S3Client.builder().build();
        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                .bucket(BUCKET_NAME).prefix(folderName).build();
        ListObjectsResponse listObjectsResponse = client.listObjects(listObjectsRequest);
        List<S3Object> contents = listObjectsResponse.contents();

        List<String> listKeys = new ArrayList<>();
        contents.forEach(content-> listKeys.add(content.key()));
        return listKeys;
    }

    public static void uploadFile(String folderName, String fileName, InputStream inputStream) {
     S3Client client = S3Client.builder().build();
     PutObjectRequest putObjectRequest = PutObjectRequest.builder()
             .bucket(BUCKET_NAME).key(folderName + "/" + fileName).acl("public-read").build();
     try(inputStream) {
         int contentLength = inputStream.available();
         client.putObject(putObjectRequest,RequestBody.fromInputStream(inputStream,contentLength));
     } catch (IOException e) {
         throw new RuntimeException(e);
     }
    }

    public static void deleteFile(String fileName) {
        S3Client client = S3Client.builder().build();
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(BUCKET_NAME)
                .key(fileName).build();
        client.deleteObject(deleteObjectRequest);
    }

    public static void removeFolder(String folderName) {
        S3Client client = S3Client.builder().build();
        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                .bucket(BUCKET_NAME).prefix(folderName).build();
        ListObjectsResponse listObjectsResponse = client.listObjects(listObjectsRequest);
        List<S3Object> contents = listObjectsResponse.contents();

        contents.forEach(content->  {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(BUCKET_NAME)
                    .key(content.key()).build();
            client.deleteObject(deleteObjectRequest);
        });
    }


}
