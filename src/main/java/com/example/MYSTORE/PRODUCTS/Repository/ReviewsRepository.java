package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Reviews;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.SECURITY.Model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews,Long> {
    List<Reviews> findByUser(User user);
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,attributePaths = {"tea"})
    @Query("select r from Reviews r where r.comment = ?1")
    Reviews NotLazyFindByComment(String comment);
    Reviews findById(long id);
    List<Reviews> findByTea(Tea tea);
}
