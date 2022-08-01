package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Category;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface TeaRepository extends JpaRepository<Tea,Long> {
    Tea findByPrice(String price);
    Tea findById(long id);
    Tea getById(Long id);
    List<Tea> findByAboutStartingWith(String about);
    Page<Tea> findByNameContainingAndPriceLessThanEqualAndPriceGreaterThanEqualAndCategoriesInOrderByNameAsc(
            String name, Integer maxprice, Integer minprice, List<Category> categories,Pageable pageable);
    Page<Tea> findByNameContainingAndPriceLessThanEqualAndPriceGreaterThanEqualOrderByNameAsc(
            String name, Integer maxprice, Integer minprice,Pageable pageable);
    Page<Tea> findAll(Pageable pageable);
}
