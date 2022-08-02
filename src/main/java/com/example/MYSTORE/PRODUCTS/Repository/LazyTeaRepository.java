package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Tea;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LazyTeaRepository extends JpaRepository<Tea,Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"reviews","teaImages","categories"})
    Tea findById(long id);
}
