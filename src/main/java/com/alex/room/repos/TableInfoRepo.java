package com.alex.room.repos;

import com.alex.room.domain.TableInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TableInfoRepo extends CrudRepository<TableInfo, Integer> {
    TableInfo findByNumber(Integer number);
    List<TableInfo> findByDateNextRep(String dateNextRep);
}
