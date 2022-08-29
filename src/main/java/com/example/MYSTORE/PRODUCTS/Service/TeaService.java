package com.example.MYSTORE.PRODUCTS.Service;

import com.example.MYSTORE.PRODUCTS.DTO.SearchDTO;
import com.example.MYSTORE.PRODUCTS.DTO.TeaDTO;
import com.example.MYSTORE.PRODUCTS.Model.Category;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Model.TeaImage;
import com.example.MYSTORE.PRODUCTS.POJO.RESULT;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomCategoryRepositoryImpl;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomTeaImageRepositoryImpl;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomTeaRepositoryImpl;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomUserRepositoryImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TeaService {
    private final CustomCategoryRepositoryImpl customCategoryRepository;
    private final FileService fileService;
    private final CustomTeaRepositoryImpl customTeaRepository;
    private final CustomTeaImageRepositoryImpl customTeaImageRepository;
    private final ProductUtils productUtils;
    private final CustomUserRepositoryImpl customUserRepository;
    @Autowired
    public TeaService(CustomTeaImageRepositoryImpl customTeaImageRepository,CustomTeaRepositoryImpl customTeaRepository,
                      FileService fileService,CustomCategoryRepositoryImpl customCategoryRepository,ProductUtils productUtils,
                      CustomUserRepositoryImpl customUserRepository) {
        this.customTeaImageRepository = customTeaImageRepository;
        this.customTeaRepository = customTeaRepository;
        this.fileService = fileService;
        this.customCategoryRepository = customCategoryRepository;
        this.productUtils = productUtils;
        this.customUserRepository = customUserRepository;
    }
    private final Gson gson = new Gson();
    public ResponseEntity saveProduct(String teaReq, MultipartFile[] files, Object mainFile, String category)
            throws IOException,ClassCastException, URISyntaxException {
        try{
            TeaDTO teaDTO = gson.fromJson(teaReq,TeaDTO.class);
            Tea tea = productUtils.DTO_to_Tea(teaDTO);
            tea.setMainLinkImage(fileService.saveFile(mainFile));
            customTeaRepository.saveNewTea(tea);
            String[] categories = category.replace("[","").replace("]","").split(",");
            for(String c : categories){
                if(!c.equals("undefined")){
                    RESULT result = gson.fromJson(c,RESULT.class);
                    Category category1 = customCategoryRepository.getCategoryByName(result.getResult());
                    if(category1 != null){customCategoryRepository.updateCategoryAndTea(category1,tea);}
                }
            }
            for(MultipartFile file : files){
                TeaImage teaImage =  new TeaImage(fileService.saveFile(file));
                customTeaImageRepository.saveNewTeaImage(teaImage);
                customTeaImageRepository.updateTeaImageAndTea(teaImage,tea);
            }
            System.out.println("save");
            return ResponseEntity.ok("saved");
        } catch (ClassCastException e){
            System.out.println(e);
            System.out.println("error save product");
            return ResponseEntity.ok("Error");
        }
    }
    public ResponseEntity uploadProduct(String tea, MultipartFile[] files,MultipartFile filemain,String category)
            throws IOException,URISyntaxException{
        try{
            TeaDTO teaDTO = gson.fromJson(tea,TeaDTO.class);
            Tea tea1 = customTeaRepository.getLazyTeaById(teaDTO.getId());
            if(files != null){
                customTeaRepository.TeaClearTeaImageById(tea1.getId());
                for(MultipartFile file : files){
                    TeaImage teaImage =  new TeaImage(fileService.saveFile(file));
                    customTeaImageRepository.saveNewTeaImage(teaImage);
                    customTeaImageRepository.updateTeaImageAndTea(teaImage,tea1);
                }
            }
            if(category != null && category.length() > 4){
                String[] categories = category.replace("[","").replace("]","").split(",");
                customTeaRepository.TeaClearCategoryById(tea1.getId());
                for(String c : categories){
                    RESULT result = gson.fromJson(c,RESULT.class);
                    Category category1 = customCategoryRepository.getCategoryByName(result.getResult());
                    if(category1 != null){customCategoryRepository.updateCategoryAndTea(category1,tea1);}
                }
            }
            if(filemain != null){tea1.setMainLinkImage(fileService.saveFile(filemain));}
            productUtils.updateTea(tea1,teaDTO);
            customTeaRepository.uploadTea(tea1);
            return ResponseEntity.ok("upload");
        } catch (ClassCastException c){
            System.out.println(c);
            return ResponseEntity.ok("invalid files");
        }
    }
    public ResponseEntity uploadCategory(String name){
        try {
            Category category = new Category(name);
            customCategoryRepository.saveNewCategory(category);
            return ResponseEntity.ok(new RESULT("category was saved"));
        } catch (ClassCastException e){
            System.out.println(e);
            return ResponseEntity.badRequest().body(new RESULT("ClassCastException"));
        }
    }
    public ResponseEntity searchTea(String search){
        System.out.println(search);
        SearchDTO searchDTO = gson.fromJson(search,SearchDTO.class);
        Set<String> categories = searchDTO.getCategoryDTOS();
        System.out.println(searchDTO);
        List<Tea> teas;
        if(categories != null && categories.size() >= 1){
            teas = customTeaRepository.findTeaByNameAndPriceAndCategoryName(
                    searchDTO.getName(),categories,searchDTO.getMinPrice(),searchDTO.getMaxPrice(), searchDTO.getPageable()
            );
        }else{
            teas = customTeaRepository.findTeaByNameAndPrice(
                    searchDTO.getName(),searchDTO.getMinPrice(),searchDTO.getMaxPrice(), searchDTO.getPageable()
            );
        }
        List<TeaDTO> teaDTOS = new ArrayList<>();
        teas.forEach(t -> teaDTOS.add(productUtils.TeaLazy_to_DTO(t)));
        SearchDTO searchDTO1 = new SearchDTO();
        searchDTO1.setTeaDTOS(teaDTOS);
        searchDTO1.setCount(searchDTO.getPageable());
        return ResponseEntity.ok(searchDTO1);
    }
    public ResponseEntity getTea(String id){
        try {
            Tea tea = customTeaRepository.getEagerTeaCategoryReviewImage(Long.parseLong(id));
            TeaDTO teaDTO = productUtils.Tea_to_DTO(tea);
            return ResponseEntity.ok(teaDTO);
        } catch (NullPointerException n){
            return ResponseEntity.badRequest().body("tea not found");
        }
    }
    public ResponseEntity addLike(String res, Principal principal){
        RESULT result = gson.fromJson(res, RESULT.class);
        User user = customUserRepository.getUserByEmail(principal.getName());
        if(user != null){
            customTeaRepository.updateTeaAndUser(Long.parseLong(result.getResult()),user.getId());
            return ResponseEntity.ok("add");
        }
        return ResponseEntity.ok(new RESULT("exc"));
    }
    public ResponseEntity delLike(String res,Principal principal){
        RESULT result = gson.fromJson(res,RESULT.class);
        User user = customUserRepository.getUserByTeaIdAndEmail(Long.parseLong(result.getResult()),principal.getName());
        if(user != null){
            customTeaRepository.deleteRelationTeaAndUser(Long.parseLong(result.getResult()), user.getId());
            return ResponseEntity.ok("Remove");
        }
        return ResponseEntity.ok("exc");
    }
    public ResponseEntity getLikes(Principal principal){
        List<Tea> teaList = customUserRepository.getUserAndTeasByEmail(principal.getName()).getTeas();
        List<TeaDTO> teaDTOS = new ArrayList<>();
        teaList.forEach(t -> {
            TeaDTO teaDTO = productUtils.TeaLazy_to_DTO(t);
            teaDTOS.add(teaDTO);
        });
        return ResponseEntity.ok(teaDTOS);
    }
}
