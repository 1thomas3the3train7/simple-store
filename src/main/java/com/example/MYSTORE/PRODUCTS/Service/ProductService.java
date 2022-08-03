package com.example.MYSTORE.PRODUCTS.Service;

import com.example.MYSTORE.PRODUCTS.DTO.*;
import com.example.MYSTORE.PRODUCTS.Model.*;
import com.example.MYSTORE.PRODUCTS.POJO.RESULT;
import com.example.MYSTORE.PRODUCTS.POJO.ReviewsJson;
import com.example.MYSTORE.PRODUCTS.POJO.TEST;
import com.example.MYSTORE.PRODUCTS.Repository.*;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductService {
    @Value("${upload.path}")
    private String path;
    @Autowired
    private TeaRepository teaRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private TeaImageRepository teaImageRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LazyTeaRepository lazyTeaRepository;
    @Autowired
    private SlaiderRepository slaiderRepository;
    @Autowired
    private TeaListsRepository teaListsRepository;

    private final Gson gson = new Gson();
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity saveProduct(String teaReq, Object[] files,Object mainFile,String category) throws IOException,ClassCastException{
        try{
            /*Gson gson = new Gson();*/
            TeaDTO teaDTO = gson.fromJson(teaReq,TeaDTO.class);
            Tea tea = new Tea();
            tea.setName(teaDTO.getName());tea.setPrice(teaDTO.getPrice());tea.setMainLinkImage(teaDTO.getMainLinkImage());
            tea.setSubname(teaDTO.getSubname());
            tea.setFermentation(teaDTO.getFermentation());tea.setAbout(teaDTO.getAbout());tea.setOldPrice(teaDTO.getOldPrice());
            for(Object file : files){
                TeaImage teaImage =  new TeaImage(fileService.saveFile(file));
                teaImage.setTea(tea);
                teaImageRepository.saveAndFlush(teaImage);
            }
            String[] categories = category.replace("[","").replace("]","").split(",");
            for(String c : categories){
                if(!c.equals("undefined")){
                    RESULT result = gson.fromJson(c,RESULT.class);
                    Category category1 = categoryRepository.findByName(result.getResult());
                    if(category1 != null){category1.addTea(tea);categoryRepository.saveAndFlush(category1);}
                }
            }
            tea.setMainLinkImage(fileService.saveFile(mainFile));
            teaRepository.save(tea);
            System.out.println("save");
            return ResponseEntity.ok("saved");
        } catch (ClassCastException e){
            System.out.println(e);
            System.out.println("error save product");
            return ResponseEntity.ok("Error");
        }
    }
    public ResponseEntity uplaodProduct(String tea, Object[] files,Object filemain,String category) throws IOException{
        try{
            /*Gson gson = new Gson();*/
            TeaDTO teaDTO = gson.fromJson(tea,TeaDTO.class);
            Tea tea1 = teaRepository.getById(teaDTO.getId());
            tea1.setAbout(teaDTO.getAbout());tea1.setPrice(teaDTO.getPrice());tea1.setName(teaDTO.getName());
            tea1.setMadeCountry(teaDTO.getMadeCountry());tea1.setFermentation(teaDTO.getFermentation());
            tea1.setSubname(teaDTO.getSubname());
            tea1.setOldPrice(teaDTO.getOldPrice());
            if(files != null){
                System.out.println("files");
                Set<TeaImage> teaImages = new HashSet<>();
                tea1.setTeaImages(teaImages);
                teaRepository.saveAndFlush(tea1);
                for(Object file : files){
                    TeaImage teaImage =  new TeaImage(fileService.saveFile(file));
                    teaImage.setTea(tea1);
                    teaImageRepository.saveAndFlush(teaImage);
                }
            }
            if(category != null && category.length() > 4){
                String[] categories = category.replace("[","").replace("]","").split(",");
                Set<Category> categories1 = new HashSet<>();
                tea1.setCategories(categories1);
                teaRepository.saveAndFlush(tea1);
                for(String c : categories){
                    RESULT result = gson.fromJson(c,RESULT.class);
                    Category category1 = categoryRepository.findByName(result.getResult());
                    if(category1 != null){category1.addTea(tea1);categoryRepository.saveAndFlush(category1);}
                }
            }
            if(filemain != null){
                tea1.setMainLinkImage(fileService.saveFile(filemain));
            }
            teaRepository.saveAndFlush(tea1);
            return ResponseEntity.ok("upload");
        } catch (ClassCastException c){
            System.out.println(c);
            return ResponseEntity.ok("invalid files");
        }
    }
    public ResponseEntity getProducts(){
        List<Tea> teaList = teaRepository.findAll();
        return ResponseEntity.ok(teaList);
    }
    public ResponseEntity upldCategory(String name){
        try {
            Category category = new Category(name);
            categoryRepository.save(category);
            return ResponseEntity.ok(new RESULT("category was saved"));
        } catch (ClassCastException e){
            System.out.println(e);
            return ResponseEntity.badRequest().body(new RESULT("ClassCastException"));
        }
    }
    public Tea TestToTea(TEST test){
        Tea tea = new Tea();
        tea.setPrice(Integer.parseInt(test.getPrice()));
        tea.setAbout(test.getAbout());
        tea.setMadeCountry(test.getMadeCountry());
        tea.setFermentation(test.getFermentation());
        return tea;
    }
    public ResponseEntity saveReview(String json, String email){
        ReviewsJson reviewsJson = gson.fromJson(json,ReviewsJson.class);
        User user = userRepository.findByEmail(email);
        Tea tea = teaRepository.findById(Long.parseLong(reviewsJson.getId()));
        if(user != null && tea != null){
            List<Reviews> reviews1 = reviewsRepository.findByTea(tea);
            Reviews reviews = new Reviews(reviewsJson.getPluses(),reviewsJson.getMinuses(),
                    reviewsJson.getComment(), reviewsJson.getGrade());
            int grd = reviews1.size();
            double grd1 = tea.getGrade();
            if(grd1 > 0){tea.setGrade(grd1 * (grd) / (grd + 1) + reviews.getGrade()/(grd + 1));}
            else {tea.setGrade(reviews.getGrade());};
            reviews.setUsername(user.getUsername());
            reviews.setTea(tea);
            reviews.setUser(user);
            reviewsRepository.save(reviews);
            user.addReview(reviews);
            tea.addReview(reviews);
            return ResponseEntity.ok(new RESULT("saved"));
        }
        return ResponseEntity.ok(null);
    }
    public ResponseEntity getReview(String id){
        /*Gson gson = new Gson();*/
        RESULT result = gson.fromJson(id,RESULT.class);
        Set<Reviews> reviewsSet = lazyTeaRepository.findById(Long.parseLong(result.getResult())).getReviews();
        if(reviewsSet == null){return ResponseEntity.ok(new RESULT("reviews not found"));}
        List<ReviewsDTO> reviewsDTOS = new ArrayList<>();
        for(Reviews r : reviewsSet){
            ReviewsDTO reviewsDTO = new ReviewsDTO(r.getPluses(),r.getMinuses(),r.getComment(),r.getGrade(),r.getUsername());
            reviewsDTOS.add(reviewsDTO);
        }
        return ResponseEntity.ok(reviewsDTOS);
    }
    public ResponseEntity getTea(String id){
        try {
            Tea tea = lazyTeaRepository.findById(Long.parseLong(id));
            TeaDTO teaDTO = new TeaDTO();
            teaDTO.setId(tea.getId());
            teaDTO.setAbout(tea.getAbout());teaDTO.setFermentation(tea.getFermentation());
            teaDTO.setGrade(tea.getGrade());teaDTO.setMadeCountry(tea.getMadeCountry());
            teaDTO.setName(tea.getName());teaDTO.setMainLinkImage(tea.getMainLinkImage());
            teaDTO.setPrice(tea.getPrice());teaDTO.setTeaImages(tea.getTeaImages().stream().toList());
            teaDTO.setOldPrice(tea.getOldPrice());teaDTO.setSubname(tea.getSubname());
            teaDTO.setReviewsDTOList(tea.getReviews().stream().map
                    (m -> new ReviewsDTO(m.getPluses(),m.getMinuses(),m.getComment(),m.getGrade(),m.getUsername())).collect(Collectors.toList()));
            teaDTO.setCategoryDTOList(tea.getCategories().stream().map(
                    m -> new CategoryDTO(m.getName())).collect(Collectors.toList()));
            return ResponseEntity.ok(teaDTO);
        } catch (NullPointerException n){
            return ResponseEntity.badRequest().body("tea not found");
        }

    }
    public ResponseEntity searchTea(String search){
        /*Gson gson = new Gson();*/
        SearchDTO searchDTO = gson.fromJson(search,SearchDTO.class);
        List<Category> categories = new ArrayList<>();
        Pageable pageable = PageRequest.of(searchDTO.getPageable() - 1,10,Sort.by(Sort.Direction.ASC,"name"));
        if(searchDTO.getCategoryDTOS() != null && searchDTO.getCategoryDTOS().length >= 1){
            for(String s : searchDTO.getCategoryDTOS()){
                Category category = categoryRepository.findByName(s);
                if(category != null){categories.add(category);}
            }
            Page<Tea> teas = teaRepository.
                    findByNameContainingAndPriceLessThanEqualAndPriceGreaterThanEqualAndCategoriesInOrderByNameAsc(searchDTO.getName(),
                    searchDTO.getMaxPrice(),searchDTO.getMinPrice(),categories,pageable);
            List<TeaDTO> teaDTOS = new ArrayList<>();
            for(Tea m : teas){
                teaDTOS.add(new TeaDTO(m.getId(),m.getName(),m.getMadeCountry(),m.getAbout(),m.getFermentation(),
                        m.getPrice(),m.getMethodCook(),m.getMainLinkImage(),m.getPresence(),m.getGrade(),m.getOldPrice()));
            }
            SearchDTO searchDTO1 = new SearchDTO();
            searchDTO1.setTeaDTOS(teaDTOS);
            searchDTO1.setCount(teas.getTotalPages());
            System.out.println(teas.getTotalPages());
            return ResponseEntity.ok(searchDTO1);
        } else {
            Page<Tea> teas = teaRepository.
                    findByNameContainingAndPriceLessThanEqualAndPriceGreaterThanEqualOrderByNameAsc(
                    searchDTO.getName(),searchDTO.getMaxPrice(),searchDTO.getMinPrice(),pageable
            );
            List<TeaDTO> teaDTOS = new ArrayList<>();
            for(Tea m : teas){
                teaDTOS.add(new TeaDTO(m.getId(),m.getName(),m.getMadeCountry(),m.getAbout(),m.getFermentation(),
                        m.getPrice(),m.getMethodCook(),m.getMainLinkImage(),m.getPresence(),m.getGrade(),m.getOldPrice()));
            }
            SearchDTO searchDTO1 = new SearchDTO();
            searchDTO1.setTeaDTOS(teaDTOS);
            searchDTO1.setCount(teas.getTotalPages());
            System.out.println(teas.getTotalPages());
            return ResponseEntity.ok(searchDTO1);
        }
    }
    public ResponseEntity addLike(String res, Principal principal){
       /* Gson gson = new Gson();*/
        RESULT result = gson.fromJson(res, RESULT.class);
        Tea tea = teaRepository.getById(Long.parseLong(result.getResult()));
        List<User> use = userRepository.findByTeasIn(List.of(tea));
        User user = userRepository.getByEmail(principal.getName());
        if(tea != null && principal.getName() != null && !use.contains(user)){
            user.addTea(tea);
            userRepository.save(user);
            return ResponseEntity.ok(new RESULT("add"));
        }
        return ResponseEntity.ok(new RESULT("exc"));
    }
    public ResponseEntity delLike(String res,Principal principal){
        /*Gson gson = new Gson();*/
        RESULT result = gson.fromJson(res,RESULT.class);
        Tea tea = teaRepository.getById(Long.parseLong(result.getResult()));
        List<User> use = userRepository.findByTeasIn(List.of(tea));
        User user = userRepository.getByEmail(principal.getName());
        if(tea != null && principal.getName() != null && use.contains(user)){
            user.delTea(tea);
            userRepository.save(user);
            return ResponseEntity.ok("Remove");
        }
        return ResponseEntity.ok("exc");
    }
    public ResponseEntity uploadSlaiderleft(MultipartFile[] multipartFiles,boolean del) throws IOException{
        SlaiderImages slaiderImages = slaiderRepository.findByName("leftslaider");
        if(del){teaImageRepository.deleteAllInBatch(slaiderImages.getTeaImages());slaiderImages.getTeaImages().clear();}
        if(slaiderImages == null){return ResponseEntity.ok("slaider not found");}
        for(MultipartFile mf : multipartFiles){
            TeaImage teaImage = new TeaImage(fileService.saveFile(mf));
            slaiderImages.addImage(teaImage);
            teaImageRepository.save(teaImage);
        }
        if(del){return ResponseEntity.ok("upload");}
        return ResponseEntity.ok("saved");
    }
    public ResponseEntity getLeftSlaider(){
        try {
            List<SlaiderDTO> slaiderDTOS = new ArrayList<>();
            List<TeaImage> teaImages = slaiderRepository.findByName("leftslaider").getTeaImages().stream().toList();
            for(TeaImage t : teaImages){
                SlaiderDTO slaiderDTO = new SlaiderDTO();
                slaiderDTO.setName(t.getLinkImage());
                slaiderDTOS.add(slaiderDTO);
            }
            return ResponseEntity.ok(slaiderDTOS);
        } catch (NullPointerException n){
            System.out.println(n);
            return ResponseEntity.badRequest().build();
        }
    }
    public ResponseEntity uploadSlaiderright(MultipartFile[] multipartFiles,boolean del) throws IOException{
        SlaiderImages slaiderImages = slaiderRepository.findByName("rightslaider");
        if(del){teaImageRepository.deleteAll(slaiderImages.getTeaImages());slaiderImages.getTeaImages().clear();}
        if(slaiderImages == null){return ResponseEntity.ok("slaider not found");}
        for(MultipartFile mf : multipartFiles){
            TeaImage teaImage = new TeaImage(fileService.saveFile(mf));
            slaiderImages.addImage(teaImage);
            teaImageRepository.save(teaImage);
        }
        if(del){return ResponseEntity.ok("upload");}
        return ResponseEntity.ok("saved");
    }
    public ResponseEntity getRightSlaider(){
        try {
            List<SlaiderDTO> slaiderDTOS = new ArrayList<>();
            List<TeaImage> teaImages = slaiderRepository.findByName("rightslaider").getTeaImages().stream().toList();
            for(TeaImage t : teaImages){
                SlaiderDTO slaiderDTO = new SlaiderDTO();
                slaiderDTO.setName(t.getLinkImage());
                slaiderDTOS.add(slaiderDTO);
            }
            return ResponseEntity.ok(slaiderDTOS);
        } catch (NullPointerException n){
            System.out.println(n);
            return ResponseEntity.badRequest().build();
        }
    }
    public ResponseEntity getLikes(Principal principal){
        List<Tea> teaList = userRepository.findByEmail(principal.getName()).getTeas();
        List<TeaDTO> teaDTOS = new ArrayList<>();
        for(Tea t : teaList){
            TeaDTO teaDTO = new TeaDTO(t.getId(),t.getName(),t.getMadeCountry(),t.getAbout(),t.getFermentation(),t.getPrice(),t.getMethodCook(),
                    t.getMainLinkImage(),t.getPresence(),t.getGrade(),t.getOldPrice());
            teaDTO.setSubname(t.getSubname());
            teaDTOS.add(teaDTO);
        }
        return ResponseEntity.ok(teaDTOS);
    }
    public ResponseEntity addInList1(String res){
        RESULT result = gson.fromJson(res,RESULT.class);
        TeaLists teaLists1 = teaListsRepository.findByName("list1");
        Tea tea = teaRepository.getById(Long.parseLong(result.getResult()));
        if(tea != null && !teaLists1.getTeas1().contains(tea)){
            tea.addList(teaLists1);
            teaRepository.saveAndFlush(tea);
            return ResponseEntity.ok("add");
        }
        return ResponseEntity.ok("already exist or tea by id not found");
    }
    public ResponseEntity delInList1(String res){
        RESULT result = gson.fromJson(res,RESULT.class);
        TeaLists teaLists = teaListsRepository.findByName("list1");
        Tea tea = teaRepository.getById(Long.parseLong(result.getResult()));
        if(tea != null && teaLists.getTeas1().contains(tea)){
            tea.delList(teaLists);
            teaRepository.saveAndFlush(tea);
            return ResponseEntity.ok("del");
        }
        return ResponseEntity.ok("already delete or tea by id not found");
    }
    public ResponseEntity addInList2(String res){
        RESULT result = gson.fromJson(res,RESULT.class);
        TeaLists teaLists1 = teaListsRepository.findByName("list2");
        Tea tea = teaRepository.getById(Long.parseLong(result.getResult()));
        if(tea != null && !teaLists1.getTeas1().contains(tea)){
            tea.addList(teaLists1);
            teaRepository.saveAndFlush(tea);
            return ResponseEntity.ok("add");
        }
        return ResponseEntity.ok("already exist or tea by id not found");
    }
    public ResponseEntity delInList2(String res){
        RESULT result = gson.fromJson(res,RESULT.class);
        TeaLists teaLists = teaListsRepository.findByName("list2");
        Tea tea = teaRepository.getById(Long.parseLong(result.getResult()));
        if(tea != null && teaLists.getTeas1().contains(tea)){
            tea.delList(teaLists);
            teaRepository.saveAndFlush(tea);
            return ResponseEntity.ok("del");
        }
        return ResponseEntity.ok("already delete or tea by id not found");
    }
}