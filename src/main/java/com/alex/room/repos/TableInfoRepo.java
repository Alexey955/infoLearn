package com.alex.room.repos;

import com.alex.room.domain.Stats;
import com.alex.room.domain.TableInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TableInfoRepo extends CrudRepository<TableInfo, Integer> {
    TableInfo findByNumber(Integer number);
    List<TableInfo> findByDateNextRep(String dateNextRep);
    List<TableInfo> findByTypeDateNextRepIsLessThanEqual(Date typeDateNextRep);

    @Query(value = "select new com.alex.room.domain.Stats(v.stage, avg(v.percentFalse), count(v)) from TableInfo v group by v.stage")
    List<Stats> findTableInfoCount();

    @Query(value = "select max(t.stage)from TableInfo t")
    Integer findMaxStage();

    List<TableInfo> findByStage(Integer stage);

    @Override
    List<TableInfo> findAll();

    List<TableInfo> findByUsername(String username);
}
