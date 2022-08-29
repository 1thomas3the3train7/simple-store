package com.example.MYSTORE.PRODUCTS.Service;

import com.example.MYSTORE.PRODUCTS.POJO.RESULT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    @Value("${upload.path}")
    private String path;
    public String saveFile(Object file)throws IOException, URISyntaxException {
        try {
            if(file == null || file == "undefined"){return null;}
            MultipartFile multipartFile = (MultipartFile) file;
            String fileName = UUID.randomUUID().toString() + multipartFile.getContentType().replace("image/",".");
            String resultSaveFile = path + fileName;
            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream bos = new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
            bos.write(bytes);
            bos.flush();
            bos.close();
            return fileName;
        }catch (ClassCastException e){
            return null;
        }
    }
    public ByteArrayResource uploadImage(String fileName) throws IOException{
        try {
            ByteArrayResource byteArrayResource = new ByteArrayResource(
                    Files.readAllBytes(Paths.get(fileName))
            );
            return byteArrayResource;
        } catch (NoSuchFileException n){
            System.out.println(n);
            return null;
        }
    }
    public ResponseEntity getImage(Object fileName) throws IOException{
        try{
            String FileName = (String) fileName;
            return ResponseEntity.ok(uploadImage(FileName));
        } catch (ClassCastException e){
            System.out.println("error getImage");
            return ResponseEntity.badRequest().body(new RESULT("invalid params"));
        }
    }
}
