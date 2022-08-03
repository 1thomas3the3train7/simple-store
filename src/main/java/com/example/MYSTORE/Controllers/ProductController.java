package com.example.MYSTORE.Controllers;

import com.example.MYSTORE.PRODUCTS.DTO.SearchDTO;
import com.example.MYSTORE.PRODUCTS.DTO.SlaiderDTO;
import com.example.MYSTORE.PRODUCTS.DTO.TeaDTO;
import com.example.MYSTORE.PRODUCTS.DTO.TeaListDTO;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Model.TeaLists;
import com.example.MYSTORE.PRODUCTS.POJO.RESULT;
import com.example.MYSTORE.PRODUCTS.Repository.*;
import com.example.MYSTORE.PRODUCTS.Service.FileService;
import com.example.MYSTORE.PRODUCTS.Service.ProductService;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TeaListsRepository teaListsRepository;
    @Autowired
    private TeaRepository teaRepository;
    @Autowired
    private CustomTeaRepository customTeaRepository;

    @PostMapping(value = "/api/product/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity productSave(@RequestParam(value = "tea",required = false) String tea,
                                      @RequestParam(value = "files") Object[] files,
                                      @RequestParam(value = "filemain") Object filemain,
                                      @RequestParam(required = false,name = "result") String  category) throws IOException, URISyntaxException {
        System.out.println(tea);
        return productService.saveProduct(tea,files,filemain,category);
    }
    @PostMapping(value = "/api/product/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity productUpload(@RequestParam(value = "tea") String tea,
                                        @RequestParam(value = "files",required = false) MultipartFile[] files,
                                        @RequestParam(value = "filemain",required = false) Object filemain,
                                        @RequestParam(value = "result",required = false) String category) throws IOException,URISyntaxException{
        return productService.uplaodProduct(tea,files,filemain,category);
    }
    @GetMapping(value = "/api/product/getImage",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity productGetImage(@RequestParam("filename") Object fileName) throws IOException {
        return fileService.getImage(fileName);
    }
    @PostMapping(value = "/api/product/getProducts")
    public ResponseEntity productsGet(){
        return productService.getProducts();
    }
    @PostMapping(value = "/api/product/getCategory")
    public ResponseEntity getCategory(){
        return ResponseEntity.ok(categoryRepository.findAll());
    }
    @PostMapping(value = "/api/product/uploadcategory",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadCategory(@RequestBody String name){
        System.out.println(name);
        Gson gson = new Gson();
        RESULT result = gson.fromJson(name,RESULT.class);
        return productService.upldCategory(result.getResult());
    }

    @PostMapping(value = "/api/product/getProductList1")
    public ResponseEntity getProductList1(){
        TeaListDTO teaListDTO = new TeaListDTO();
        teaListDTO.setTeas(teaListsRepository.findByName("list1").getTeas1().stream().toList());
        return ResponseEntity.ok(teaListDTO);
    }
    @PostMapping(value = "/api/product/getProductList2")
    public ResponseEntity getProductList2(){
        TeaListDTO teaListDTO = new TeaListDTO();
        teaListDTO.setTeas(teaListsRepository.findByName("list2").getTeas1().stream().toList());
        return ResponseEntity.ok(teaListDTO);
    }
    @PostMapping(value = "/api/product/getproductbyid")
    public ResponseEntity getProductById(@RequestBody String res){
        System.out.println(res);
        Gson gson = new Gson();
        RESULT result = gson.fromJson(res,RESULT.class);
        return productService.getTea(result.getResult());
    }
    @PostMapping(value = "/api/product/savereview")
    public ResponseEntity saveReview(@RequestBody String bodyreview, Principal principal){
        if(principal != null){
            return productService.saveReview(bodyreview, principal.getName());
        }
        return ResponseEntity.ok(new RESULT("not authorization"));
    }
    @PostMapping(value = "/api/product/getReview")
    public ResponseEntity getReview(@RequestBody String id){
        return productService.getReview(id);
    }
    @PostMapping(value = "/api/product/search")
    public ResponseEntity getSearch(@RequestBody String search){
        return productService.searchTea(search);
    }
    @PostMapping(value = "/api/product/searchAll")
    public ResponseEntity getSearchAll(){
        Pageable pageable = PageRequest.of(0,10);
        return ResponseEntity.ok(teaRepository.findAll(pageable));
    }
    @PostMapping(value = "/api/product/addLike")
    public ResponseEntity AddLike(@RequestBody String res,Principal principal){
        return productService.addLike(res,principal);
    }
    @PostMapping(value = "/api/product/delLike")
    public ResponseEntity DelLike(@RequestBody String res,Principal principal){
        return productService.delLike(res,principal);
    }
    @PostMapping(value = "/api/product/getLikes")
    public ResponseEntity GetLikes(Principal principal){
        return productService.getLikes(principal);
    }
    @PostMapping(value = "/api/product/getPriceInfo")
    public ResponseEntity GetPrice(){
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setMaxPrice(customTeaRepository.findMaxPrice());
        searchDTO.setMinPrice(customTeaRepository.findMinPrice());
        return ResponseEntity.ok(searchDTO);
    }
    @PostMapping(value = "/api/product/addinList1")
    public ResponseEntity addinList(@RequestBody String res){
        return productService.addInList1(res);
    }
    @PostMapping(value = "/api/product/delinList1")
    public ResponseEntity delinList1(@RequestBody String res){
        return productService.delInList1(res);
    }
    @PostMapping(value = "/api/product/addinList2")
    public ResponseEntity addinList2(@RequestBody String res){
        return productService.addInList2(res);
    }
    @PostMapping(value = "/api/product/delinList2")
    public ResponseEntity delinList2(@RequestBody String res){
        return productService.delInList2(res);
    }
    @PostMapping(value = "/api/product/leftSlaider",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadSlaiderleft(
            @RequestParam("files") MultipartFile[] files) throws IOException,URISyntaxException{
        return productService.uploadSlaiderleft(files,false);
    }
    @PostMapping(value = "/api/product/leftSlaiderD",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadSlaiderleftD(
            @RequestParam("files") MultipartFile[] files) throws IOException,URISyntaxException{
        return productService.uploadSlaiderleft(files,true);
    }
    @PostMapping(value = "/api/product/getLeftSlaider")
    public ResponseEntity getleftslaider(){
        return productService.getLeftSlaider();
    }
    @PostMapping(value = "/api/product/rightSlaider",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadSlaiderRight(
            @RequestParam("files") MultipartFile[] files) throws IOException,URISyntaxException{
        return productService.uploadSlaiderright(files,false);
    }
    @PostMapping(value = "/api/product/rightSlaiderD",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadSlaiderRightD(
            @RequestParam("files") MultipartFile[] files) throws IOException,URISyntaxException{
        return productService.uploadSlaiderright(files,true);
    }
    @PostMapping(value = "/api/product/getRightSlaider")
    public ResponseEntity getrightslaider(){
        return productService.getRightSlaider();
    }

}
