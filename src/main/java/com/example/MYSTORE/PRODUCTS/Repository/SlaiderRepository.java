package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.SlaiderImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaiderRepository extends JpaRepository<SlaiderImages,Long> {
    SlaiderImages findByName(String name);
}
