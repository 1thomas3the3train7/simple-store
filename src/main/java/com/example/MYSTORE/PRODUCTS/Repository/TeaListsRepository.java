package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.TeaLists;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeaListsRepository extends JpaRepository<TeaLists,Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,attributePaths = {"teas1"})
    TeaLists findByName(String name);
}
