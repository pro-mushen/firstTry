package ru.tander.services;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import ru.tander.config.Config;
import ru.tander.xml.ParsingXml;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XmlServiceImp implements XmlService {

    private static final Logger LOGGER = Logger.getLogger(XmlServiceImp.class);
    /**
     * Даный класс предаставляет функционал для работы с XML
     */
    private ParsingXml parsingXml;
    /** Даный класс предаставляет необходимые данные для создания XML*/
    private InitData initData;

    public XmlServiceImp(ParsingXml parsingXml, InitData initData) {
        this.parsingXml = parsingXml;
        this.initData = initData;
    }

    @Override
    public long getSumFields(int countField) {
        String originalXml = Config.getProperty(Config.ORIGINAL_XML);
        String transformXml = Config.getProperty(Config.TRANSFORM_XML);
        String xsl = Config.getProperty(Config.XSL);

        long sum = 0;
        String[] fields = initData.getData(countField);
        parsingXml.createXmlFile(fields, originalXml);
        parsingXml.transformXml(originalXml, xsl, transformXml);
        try {
            sum = parsingXml.sumFields(transformXml);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            LOGGER.error(e);
        }
        return sum;
    }


    public ParsingXml getParsingXml() {
        return parsingXml;
    }

    public void setParsingXml(ParsingXml parsingXml) {
        this.parsingXml = parsingXml;
    }

    public InitData getInitData() {
        return initData;
    }

    public void setInitData(InitData initData) {
        this.initData = initData;
    }
}
