package ru.tander.services;

/**
 * Интерфейс предоставляет данные для формирования XML для {@link XmlService}.
 *
 * @author Мирзоян Мушег
 * @version 1.0
 * @see InitTestDb
 */

public interface InitData {
    String[] getData(int countField);
}
