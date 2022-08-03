package com.example.MYSTORE.PRODUCTS.Repository;


import com.example.MYSTORE.PRODUCTS.Model.TeaImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeaImageRepository extends JpaRepository<TeaImage,Long> {
}
