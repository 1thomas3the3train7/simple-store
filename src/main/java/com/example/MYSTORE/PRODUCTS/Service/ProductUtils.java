package com.example.MYSTORE.PRODUCTS.Service;

import com.example.MYSTORE.PRODUCTS.DTO.CategoryDTO;
import com.example.MYSTORE.PRODUCTS.DTO.ReviewsDTO;
import com.example.MYSTORE.PRODUCTS.DTO.TeaDTO;
import com.example.MYSTORE.PRODUCTS.Model.Reviews;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductUtils {
    public Tea DTO_to_Tea(TeaDTO teaDTO){
        Tea tea = new Tea();
        if(teaDTO.getId() != null){tea.setId(teaDTO.getId());}
        tea.setName(teaDTO.getName());tea.setPrice(teaDTO.getPrice());tea.setMainLinkImage(teaDTO.getMainLinkImage());
        tea.setSubname(teaDTO.getSubname());
        tea.setFermentation(teaDTO.getFermentation());tea.setAbout(teaDTO.getAbout());tea.setOldPrice(teaDTO.getOldPrice());
        return tea;
    }
    public TeaDTO Tea_to_DTO(Tea tea){
        TeaDTO teaDTO = new TeaDTO();
        teaDTO.setId(tea.getId());
        teaDTO.setAbout(tea.getAbout());teaDTO.setFermentation(tea.getFermentation());
        teaDTO.setGrade(tea.getGrade());teaDTO.setMadeCountry(tea.getMadeCountry());
        teaDTO.setName(tea.getName());teaDTO.setMainLinkImage(tea.getMainLinkImage());
        teaDTO.setPrice(tea.getPrice());teaDTO.setTeaImages(tea.getTeaImages().stream().toList());
        teaDTO.setOldPrice(tea.getOldPrice());teaDTO.setSubname(tea.getSubname());
        teaDTO.setReviewsDTOList(tea.getReviews().stream().map
                (m -> new ReviewsDTO(m.getPluses(),m.getMinuses(),m.getComment(),m.getGrade(),
                        m.getUsername())).collect(Collectors.toList()));
        teaDTO.setCategoryDTOList(tea.getCategories().stream().map(
                m -> new CategoryDTO(m.getName())).collect(Collectors.toList()));
        return teaDTO;
    }
    public TeaDTO TeaLazy_to_DTO(Tea tea){
        TeaDTO teaDTO = new TeaDTO();
        teaDTO.setId(tea.getId());teaDTO.setPrice(teaDTO.getPrice());
        teaDTO.setOldPrice(tea.getOldPrice());teaDTO.setSubname(tea.getSubname());
        teaDTO.setMainLinkImage(tea.getMainLinkImage());teaDTO.setGrade(tea.getGrade());
        return teaDTO;
    }
    public void updateTea(Tea tea,TeaDTO teaDTO){
        tea.setName(teaDTO.getName());tea.setSubname(teaDTO.getSubname());
        tea.setPrice(teaDTO.getPrice());tea.setOldPrice(teaDTO.getPrice());
    }
    public ReviewsDTO Review_To_DTO(Reviews r){
        return new ReviewsDTO(r.getPluses(),r.getMinuses(),r.getComment(),r.getGrade(),r.getUsername());
    }
}
