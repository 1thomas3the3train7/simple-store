package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Model.TeaLists;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TeaListsRepository extends JpaRepository<TeaLists,Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,attributePaths = {"teas1"})
    TeaLists findByName(String name);
    TeaLists findByTeas1(Tea tea);
}
