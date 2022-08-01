package com.example.MYSTORE.PRODUCTS.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SearchDTO {
    private String name;
    private Integer minPrice;
    private Integer maxPrice;
    private String[] categoryDTOS;
    private int pageable;
    private List<TeaDTO> teaDTOS;
    private int count;
}
