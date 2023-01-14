package com.whn.content_service.utils;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2023/1/3 15:37
 */

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinIoTemplate {
    public String secretKey;
    public String accessKey;
    public String bucket;
    public String endPoint;

    private MinioClient minioClient;


    @PostConstruct
    public void init() {
        minioClient = MinioClient
                .builder()
                .credentials(accessKey, secretKey)
                .endpoint(endPoint)
                .build();
        ;
    }

    private final static String SEPARATOR = "/";

    /**
     * @param dirPath
     * @param filename yyyy/mm/dd/file.jpg
     * @return
     */
    private String builderFilePath(String dirPath, String filename) {
        StringBuilder stringBuilder = new StringBuilder(50);
        if (!StringUtils.isEmpty(dirPath)) {
            stringBuilder.append(dirPath).append(SEPARATOR);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = sdf.format(new Date());
        stringBuilder.append(todayStr).append(SEPARATOR).append((System.currentTimeMillis())).append(SEPARATOR);
        stringBuilder.append(filename);
        return stringBuilder.toString();
    }

    /**
     * 上传图片文件
     *
     * @param prefix        文件前缀
     * @param multipartFile 文件
     * @return 文件全路径
     */
    public String uploadImgFile(String prefix, MultipartFile multipartFile) {
        StringBuilder urlPath= new StringBuilder("");
        String fileName = multipartFile.getOriginalFilename();
        InputStream inputStream;
        String filePath = builderFilePath(prefix, fileName);
        File file = null;
        try {
            file = File.createTempFile("file_", fileName);
            multipartFile.transferTo(file);
            inputStream = new FileInputStream(file);
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .bucket(bucket).stream(inputStream, inputStream.available(), -1)
                    .build();
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
            minioClient.putObject(putObjectArgs);
             urlPath = new StringBuilder(endPoint);
            urlPath.append(SEPARATOR + bucket);
            urlPath.append(SEPARATOR);
            urlPath.append(filePath);
        } catch (Exception ex) {
            log.error("文件上传失败");
            log.error("minio put file error.", ex);
        }
        return urlPath.toString();
    }

    /**
     * 删除文件
     *
     * @param pathUrls 文件全路径列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> pathUrls) {
        List<DeleteObject> list = new ArrayList<>();
        pathUrls.forEach(pathUrl -> {
            String filePath = pathUrl.replace(endPoint + "/"+ bucket+ "/", "");
            list.add(new DeleteObject(filePath));
        });
        // 删除Objects
        RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder().bucket(bucket).objects(list).build();
        Iterable<Result<DeleteError>> iterable = minioClient.removeObjects(removeObjectsArgs);
        try {
            for (Result<DeleteError> result : iterable) {
                DeleteError error = result.get();
                log.info("minio删除错误->bucketName={},objectName={},message={}", error.bucketName(), error.objectName(), error.message());
                throw new RuntimeException("minio删除错误->bucketName="+ error.bucketName()+",objectName="+ error.objectName()+",message="+ error.bucketName()+ error.message());
            }
        } catch (Exception e) {
            log.error("读取minio删除错误的数据时异常", e);
        }
    }


    /**
     * 删除文件
     *
     * @param pathUrl 文件全路径列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(String pathUrl) {
        String filePath = pathUrl.replace(endPoint + "/"+ bucket+ "/", "");
        // 删除Objects
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucket).object(filePath).build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("读取minio删除错误的数据时异常", e);
        }
    }


    /**
     * 下载文件
     *
     * @param pathUrl 文件全路径
     * @return 文件流
     */
    public byte[] downLoadFile(String pathUrl) {
        String key = pathUrl.replace(endPoint + "/", "");
        int index = key.indexOf(SEPARATOR);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(filePath).build());
        } catch (Exception e) {
            log.error("minio down file error.  pathUrl:{}", pathUrl);
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while (true) {
            try {
                if (!((rc = inputStream.read(buff, 0, 100)) > 0)) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
