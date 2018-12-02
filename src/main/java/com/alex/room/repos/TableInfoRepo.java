package com.alex.room.repos;

import com.alex.room.domain.TableInfo;
import org.springframework.data.repository.CrudRepository;

public interface TableInfoRepo extends CrudRepository<TableInfo, Integer> {
    TableInfo findByNumber(Integer number);
}
