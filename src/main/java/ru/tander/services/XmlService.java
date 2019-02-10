package ru.tander.services;

/**
 * Интерфейс служит для обёртки всей работы с XML.
 * Lля упрощения интерфейса, добавления гибкости и изолированости работы с XML
 *
 * @author Мирзоян Мушег
 * @version 1.0
 * @see XmlServiceImp
 */
public interface XmlService {
    long getSumFields(int countField);
}
