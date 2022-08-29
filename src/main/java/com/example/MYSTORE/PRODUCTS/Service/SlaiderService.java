package com.example.MYSTORE.PRODUCTS.Service;

import com.example.MYSTORE.PRODUCTS.DTO.SlaiderDTO;
import com.example.MYSTORE.PRODUCTS.Model.SlaiderImages;
import com.example.MYSTORE.PRODUCTS.Model.TeaImage;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomSlaiderRepositoryImpl;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomTeaImageRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlaiderService {
    private final CustomSlaiderRepositoryImpl customSlaiderRepository;
    private final CustomTeaImageRepositoryImpl customTeaImageRepository;
    private final FileService fileService;
    @Autowired
    public SlaiderService(CustomSlaiderRepositoryImpl customSlaiderRepository,
                          CustomTeaImageRepositoryImpl customTeaImageRepository, FileService fileService) {
        this.customSlaiderRepository = customSlaiderRepository;
        this.customTeaImageRepository = customTeaImageRepository;
        this.fileService = fileService;
    }

    public ResponseEntity uploadSlaiderleft(MultipartFile[] multipartFiles, boolean del) throws IOException, URISyntaxException {
        SlaiderImages slaiderImages = customSlaiderRepository.getSlaiderAndTeaImageByName("leftslaider");
        if(slaiderImages == null){return ResponseEntity.ok("slaider not found");}
        if(del){customTeaImageRepository.deleteTeaImageBySlaider(slaiderImages);}
        for(MultipartFile mf : multipartFiles){
            TeaImage teaImage = new TeaImage(fileService.saveFile(mf));
            customTeaImageRepository.saveNewTeaImage(teaImage);
            customSlaiderRepository.updateSlaiderAndTeaImage(slaiderImages,teaImage);
        }
        if(del){return ResponseEntity.ok("upload");}
        return ResponseEntity.ok("saved");
    }
    public ResponseEntity getLeftSlaider(){
        try {
            List<SlaiderDTO> slaiderDTOS = new ArrayList<>();
            customSlaiderRepository.getSlaiderAndTeaImageByName("leftslaider")
                    .getTeaImages().stream().toList().forEach(c -> {
                        SlaiderDTO slaiderDTO = new SlaiderDTO();
                        slaiderDTO.setName(c.getLinkImage());
                        slaiderDTOS.add(slaiderDTO);
                    });
            return ResponseEntity.ok(slaiderDTOS);
        } catch (NullPointerException n){
            System.out.println(n);
            return ResponseEntity.badRequest().build();
        }
    }
    public ResponseEntity uploadSlaiderright(MultipartFile[] multipartFiles,boolean del) throws IOException,URISyntaxException{
        SlaiderImages slaiderImages = customSlaiderRepository.getSlaiderAndTeaImageByName("rightslaider");
        if(slaiderImages == null){return ResponseEntity.ok("slaider not found");}
        if(del){customTeaImageRepository.deleteTeaImageBySlaider(slaiderImages);}
        for(MultipartFile mf : multipartFiles){
            TeaImage teaImage = new TeaImage(fileService.saveFile(mf));
            customTeaImageRepository.saveNewTeaImage(teaImage);
            customSlaiderRepository.updateSlaiderAndTeaImage(slaiderImages,teaImage);
        }
        if(del){return ResponseEntity.ok("upload");}
        return ResponseEntity.ok("saved");
    }
    public ResponseEntity getRightSlaider(){
        try {
            List<SlaiderDTO> slaiderDTOS = new ArrayList<>();
            customSlaiderRepository.getSlaiderAndTeaImageByName("rightslaider")
                    .getTeaImages().forEach(c -> {
                        SlaiderDTO slaiderDTO = new SlaiderDTO();
                        slaiderDTO.setName(c.getLinkImage());
                        slaiderDTOS.add(slaiderDTO);
                    });
            return ResponseEntity.ok(slaiderDTOS);
        } catch (NullPointerException n){
            System.out.println(n);
            return ResponseEntity.badRequest().build();
        }
    }
}
