package ru.tander.bd.dao;

/**
 * Интерфейс работы с БД
 *
 * @author Мирзоян Мушег
 * @version 1.0
 * @see TestDaoImp
 */

public interface TestDao {

    /**
     * Добавить записи в таблицу.
     * @param  n  количество записей необходимые добавить в таблицу.
     * @return количество добавленных записей.
     */
    int addNumbers(int n);

    /**
     * Очистить таблицу.
     * @return количество очищенных записей.
     */
    int clearTable();

    /**
     * Выбрать все данные таблицы.
     * @return записи таблицы в виде массива.
     */
    String[] selectAll();
}
