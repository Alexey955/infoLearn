package com.alex.room.repos;

import com.alex.room.domain.Stats;
import com.alex.room.domain.TableInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TableInfoRepo extends CrudRepository<TableInfo, Integer> {

    @Query(value = "select new com.alex.room.domain.Stats(t.stage, avg(t.percentFalse), count(t)) from TableInfo t where t.username = ?1 group by t.stage")
    List<Stats> findTableInfoCount(String username);

    @Query(value = "select new com.alex.room.domain.Stats(t.stage, avg(t.percentFalse), count(t)) from TableInfo t group by t.stage")
    List<Stats> findFullTableInfoCount();

    @Query(value = "select max(t.stage)from TableInfo t where t.username = ?1")
    Integer findMaxStage(String username);

    List<TableInfo> findByStageAndUsername(Integer stage, String username);

    @Override
    List<TableInfo> findAll();

    List<TableInfo> findByUsername(String username);

    @Query(value = "select t.id from TableInfo t where t.number = ?1 and t.username = ?2")
    Integer findTableInfoIdByNumberAndUsername(Integer number, String username);

    TableInfo findByNumberAndUsername(int number, String username);

    List<TableInfo> findByUsernameAndTypeDateNextRepIsLessThanEqual(String username, LocalDate typeDateNextRep);

    List<TableInfo> findByTypeDateNextRepAndUsername(LocalDate dateNextRep, String username);

    @Query(value = "select avg(t.percentFalse) from TableInfo t where t.username = ?1")
    Integer countAvgPercentFalse(String username);

    @Query(value = "select avg(t.percentFalse) from TableInfo t")
    Integer countFullAvgPercentFalse();

    @Query(value = "select t.amountElem from TableInfo t where t.username = ?1 and t.number = ?2")
    Integer findAmountElemByUsernameAndNumber(String username, Integer number);
}
