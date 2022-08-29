package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.TeaLists;

public interface CustomTeaListRepository {
    void saveNewTeaList(TeaLists teaLists);
    void deleteTeaList(TeaLists teaLists);
    TeaLists getTeaListByName(String name);
    void uploadTeaListAndTeaById(Long tid,Long tlid);
    void deleteRelationTeaListAndTeaByName(Long tlname,Long tid);
}
