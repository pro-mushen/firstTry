package ru.tander.xml;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class ParsingXmlImpTest {

    private static final String TEST_XML = "test.xml";
    private static final String[] FIELDS = {"1,2"};

    @BeforeClass
    public static void delXML() {
        delFile(TEST_XML);
    }

    private static void delFile(String pathFile) {
        if (pathFile != null) {
            File file = new File(pathFile);
            file.delete();
        }
    }

    @Test
    public void createXmlFile() {
        ParsingXml parsingXml = new ParsingXmlImp();
        int countAddFields = parsingXml.createXmlFile(FIELDS, TEST_XML);
        Assert.assertTrue(countAddFields == FIELDS.length);
    }

    @Test
    public void transformXml() {
    }


}