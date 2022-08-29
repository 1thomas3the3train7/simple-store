package com.example.MYSTORE.PRODUCTS.Service;

import com.example.MYSTORE.PRODUCTS.Model.TeaLists;
import com.example.MYSTORE.PRODUCTS.POJO.RESULT;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomTeaListRepositoryImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TeaListService {
    private final CustomTeaListRepositoryImpl customTeaListRepository;
    @Autowired
    public TeaListService(CustomTeaListRepositoryImpl customTeaListRepository) {
        this.customTeaListRepository = customTeaListRepository;
    }
    private final Gson gson = new Gson();
    public ResponseEntity addInList1(String res){
        RESULT result = gson.fromJson(res,RESULT.class);
        TeaLists teaLists1 = customTeaListRepository.getTeaListByName("list1");
        customTeaListRepository.uploadTeaListAndTeaById(Long.parseLong(result.getResult()), teaLists1.getId());
        return ResponseEntity.ok("add");
    }
    public ResponseEntity delInList1(String res){
        RESULT result = gson.fromJson(res,RESULT.class);
        TeaLists teaLists = customTeaListRepository.getTeaListByName("list1");
        customTeaListRepository.deleteRelationTeaListAndTeaByName(teaLists.getId(),Long.parseLong(result.getResult()));
        return ResponseEntity.ok("delete");
    }
    public ResponseEntity addInList2(String res){
        RESULT result = gson.fromJson(res,RESULT.class);
        TeaLists teaLists1 = customTeaListRepository.getTeaListByName("list2");
        customTeaListRepository.uploadTeaListAndTeaById(Long.parseLong(result.getResult()), teaLists1.getId());
        return ResponseEntity.ok("add");
    }
    public ResponseEntity delInList2(String res){
        RESULT result = gson.fromJson(res,RESULT.class);
        TeaLists teaLists = customTeaListRepository.getTeaListByName("list2");
        customTeaListRepository.deleteRelationTeaListAndTeaByName(teaLists.getId(),Long.parseLong(result.getResult()));
        return ResponseEntity.ok("delete");
    }
}
