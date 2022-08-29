package com.example.MYSTORE.PRODUCTS.Service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.*;


@Service
public class ProductService {
    @Value("${upload.path}")
    private String path;

    private final TeaListService teaListService;
    private final TeaService teaService;
    private final SlaiderService slaiderService;
    private final ReviewService reviewService;

    public ProductService(TeaListService teaListService, TeaService teaService,
                          SlaiderService slaiderService, ReviewService reviewService) {
        this.teaListService = teaListService;
        this.teaService = teaService;
        this.slaiderService = slaiderService;
        this.reviewService = reviewService;
    }

    public ResponseEntity saveProduct(String teaReq, MultipartFile[] files, Object mainFile, String category)
            throws IOException,URISyntaxException{
        return teaService.saveProduct(teaReq,files,mainFile,category);
    }
    public ResponseEntity uplaodProduct(String tea, MultipartFile[] files,MultipartFile filemain,String category)
            throws IOException,URISyntaxException{
        return teaService.uploadProduct(tea,files,filemain,category);
    }
    public ResponseEntity uploadCategory(String name){
        return teaService.uploadCategory(name);
    }
    public ResponseEntity saveReview(String json, String email){
        return reviewService.saveReview(json,email);
    }
    public ResponseEntity getReview(String id){
        return reviewService.getReview(id);
    }
    public ResponseEntity getTea(String id){
        return teaService.getTea(id);
    }
    public ResponseEntity searchTea(String search){
        return teaService.searchTea(search);
    }
    public ResponseEntity addLike(String res, Principal principal){
        return teaService.addLike(res,principal);
    }
    public ResponseEntity delLike(String res,Principal principal){
        return teaService.delLike(res,principal);
    }
    public ResponseEntity uploadSlaiderleft(MultipartFile[] multipartFiles,boolean del)
            throws IOException,URISyntaxException{
        return slaiderService.uploadSlaiderleft(multipartFiles,del);
    }
    public ResponseEntity getLeftSlaider(){
        return slaiderService.getLeftSlaider();
    }
    public ResponseEntity uploadSlaiderright(MultipartFile[] multipartFiles,boolean del)
            throws IOException,URISyntaxException{
        return slaiderService.uploadSlaiderright(multipartFiles, del);
    }
    public ResponseEntity getRightSlaider(){
        return slaiderService.getRightSlaider();
    }
    public ResponseEntity getLikes(Principal principal){
        return teaService.getLikes(principal);
    }
    public ResponseEntity addInList1(String res){
        return teaListService.addInList1(res);
    }
    public ResponseEntity delInList1(String res){
        return teaListService.delInList1(res);
    }
    public ResponseEntity addInList2(String res){
        return teaListService.addInList2(res);
    }
    public ResponseEntity delInList2(String res){
        return teaListService.delInList2(res);
    }
}