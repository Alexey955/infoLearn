package utils;


import com.alex.room.domain.TableInfo;
import com.alex.room.domain.User;
import com.alex.room.enums.Periods;
import com.alex.room.enums.Roles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtils {

    public static User user = new User((long)1, "UserOne", "111111", Roles.USER);
    public static User valery = new User((long)200, "Valery", "$2a$08$e8MNmnN1QxagVhk5eeYglOxZmOZ736UoXEi6i.iir0eHG6Yide9z2", Roles.USER);
    public static User alexandr = new User((long)300, "Alexandr", "$2a$08$7UU1MwezIj2SMG88Md2RzuIAMHMRox7BqYhSpt0L8yj3o2/fNcl3e", Roles.ADMIN);
    public static User dmitry = new User((long)400, "Dmitry", "$2a$08$nknCJz1B9Qq9R6qb.ALbV.5RiU8D0oKZm/NdwtTLxlvdw7rM9YsWO", Roles.USER);
    public static User admin = new User((long)3, "AdminOne", "111111", Roles.ADMIN);

    public static LocalDate datePriorRep = LocalDate.now();
    public static LocalDate dateNextRep = datePriorRep.plusDays(Periods.TWO.getDayAmount());

    public static String strDateBeforeToday = datePriorRep.minusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    public static String strDatePriorRep = datePriorRep.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    public static String strDateNextRep = dateNextRep.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    public static TableInfo tableInfoNull = new TableInfo(0, 10, 1, 10, strDatePriorRep, strDateNextRep, 1);
    public static TableInfo tableInfo = new TableInfo(1, 10, 1, 10, strDatePriorRep, strDateNextRep, 1);
    public static TableInfo tableInfoTwo = new TableInfo(2, 10, 1, 10, strDatePriorRep, strDateNextRep, 1);
    public static TableInfo tableInfoFive = new TableInfo(5, 10, 1, 10, strDatePriorRep, strDateNextRep, 1);

    public static TableInfo tableInfoErrorOne = new TableInfo();
    static {
        tableInfoErrorOne.setNumber(1);
        tableInfoErrorOne.setAmountElem(10);
        tableInfoErrorOne.setAmountMistakes(1);
        tableInfoErrorOne.setPercentFalse(10);
        tableInfoErrorOne.setStage(1);

        tableInfoErrorOne.setDatePriorRep("01.01.01");
        tableInfoErrorOne.setDateNextRep("01.01.01");
    }

    public static TableInfo tableInfoErrorTwo = new TableInfo();
    static {
        tableInfoErrorTwo.setNumber(1);
        tableInfoErrorTwo.setAmountElem(10);
        tableInfoErrorTwo.setAmountMistakes(1);
        tableInfoErrorTwo.setPercentFalse(10);
        tableInfoErrorTwo.setStage(1);

        tableInfoErrorTwo.setDatePriorRep(datePriorRep.plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        tableInfoErrorTwo.setDateNextRep(dateNextRep.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    public static TableInfo tableInfoErrorThree = new TableInfo();
    static {
        tableInfoErrorThree.setNumber(1);
        tableInfoErrorThree.setAmountElem(10);
        tableInfoErrorThree.setAmountMistakes(1);
        tableInfoErrorThree.setPercentFalse(10);
        tableInfoErrorThree.setStage(1);

        tableInfoErrorThree.setDatePriorRep(datePriorRep.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        tableInfoErrorThree.setDateNextRep(dateNextRep.minusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }
}
