package com.example.MYSTORE.PRODUCTS.Service;

import com.example.MYSTORE.PRODUCTS.POJO.RESULT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    @Value("${upload.path}")
    private String path;
    public String saveFile(Object file)throws IOException {
        try {
            if(file == null || file == "undefined"){return null;}
            MultipartFile multipartFile = (MultipartFile) file;
            String fileName = UUID.randomUUID().toString() + multipartFile.getContentType().replace("image/",".");
            String resultSaveFile = path + fileName;
            byte[] bytes = multipartFile.getBytes();
            Path path1 = Paths.get(resultSaveFile);
            Files.write(path1,bytes);
            System.out.println("save image");
            return fileName;
        }catch (ClassCastException e){
            System.out.println("not save image");
            System.out.println(e);
            return null;
        } catch (NullPointerException n){
            return null;
        }
    }
    public ByteArrayResource uploadImage(String fileName) throws IOException{
        ByteArrayResource byteArrayResource = new ByteArrayResource(
                Files.readAllBytes(Paths.get(path + fileName))
        );
        return byteArrayResource;
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
