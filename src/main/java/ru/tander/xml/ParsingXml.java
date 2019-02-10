package ru.tander.xml;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Создание, преобразование и считывание XML
 *
 * @author Мирзоян Мушег
 * @version 1.0
 * @see ParsingXmlImp
 */

public interface ParsingXml {

    /**
     * Создание XML файла
     * @param  fields  массив String с полями field
     * @param  xmlPath  путь для созданного файла
     */
    int createXmlFile(String[] fields, String xmlPath);

    /**
     * Преобразование XML файла
     * @param  xmlPathOriginal  путь к XML файлу, который необходимо преобразовать
     * @param  xslPath  путь к файлу XSL
     * @param  xmlPathTransformed  путь для создания нового преобразованного XML файла
     */
    void transformXml(String xmlPathOriginal, String xslPath, String xmlPathTransformed);

    /**
     * Обработка XML файла и расчёт суммы значения аттрибута field
     * @param  pathFile  путь к XML файлу, который необходимо обработать
     */
    long sumFields(String pathFile) throws IOException, ParserConfigurationException, SAXException;
}
