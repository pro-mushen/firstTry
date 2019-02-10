package ru.tander.xml;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParsingXmlImpTest {

    private static final String ORIGINAL_XML_TEST = "1_test.xml";
    private static final String TRANSFORM_XML_TEST = "2_test.xml";
    private static final String XSL = "test.xsl";
    private static final String[] FIELDS = {"1,2"};
    private static final long SUM_FIELDS = 3;
    private static final Logger LOGGER = Logger.getLogger(ParsingXmlImpTest.class);
    private static ParsingXml parsingXml = new ParsingXmlImp();

    @BeforeClass
    public static void startTest() {
        LOGGER.info("Start test");
    }

    @AfterClass
    public static void finishTest() {
        delFiles(ORIGINAL_XML_TEST, TRANSFORM_XML_TEST);
        LOGGER.info("End test");
    }

    private static int delFiles(String... files) {
        int countFiles = 0;
        for (String pathFile : files) {
            if (pathFile != null) {
                try {
                    Files.deleteIfExists(Paths.get(pathFile));
                    countFiles++;
                    LOGGER.info("File " + pathFile + " deleted");
                } catch (IOException e) {
                    LOGGER.error(e);
                }
            }
        }
        return countFiles;
    }

    @Test
    public void createXmlFile() {
        delFiles(ORIGINAL_XML_TEST);
        int countAddFields = parsingXml.createXmlFile(FIELDS, ORIGINAL_XML_TEST);
        Assert.assertEquals(countAddFields, FIELDS.length);
        Assert.assertTrue(new File(ORIGINAL_XML_TEST).exists());
    }

    @Test
    public void createXmlFileFieldsNull() throws IOException {
        File file = createFile(ORIGINAL_XML_TEST, "");
        int countAddFields = parsingXml.createXmlFile(null, ORIGINAL_XML_TEST);
        Assert.assertEquals(countAddFields, 0);
        Assert.assertFalse(file.exists());
    }

    @Test
    public void createXmlFilePathNull() {
        int countAddFields = parsingXml.createXmlFile(FIELDS, null);
        Assert.assertEquals(countAddFields, 0);
    }

    @Test
    public void transformXml() throws IOException {
        delFiles(TRANSFORM_XML_TEST);
        createFile(ORIGINAL_XML_TEST, getOriginalXmlText());
        parsingXml.transformXml(ORIGINAL_XML_TEST, XSL, TRANSFORM_XML_TEST);
        Assert.assertTrue(equalsFileText(TRANSFORM_XML_TEST, getTransformXmlText()));
    }

    private File createFile(String filePath, String text) throws IOException {
        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(text);
            writer.flush();
        }
        return file;
    }

    private boolean equalsFileText(String filePath, String text) throws IOException {
        byte[] bytesFile = Files.readAllBytes(Paths.get(filePath));
        String textFile = new String(bytesFile, StandardCharsets.UTF_8);
        return textFile.equals(text);
    }

    @Test
    public void sumFields() throws IOException, ParserConfigurationException, SAXException {
        createFile(TRANSFORM_XML_TEST, getTransformXmlText());
        long sum = parsingXml.sumFields(TRANSFORM_XML_TEST);
        Assert.assertEquals(sum, SUM_FIELDS);
    }

    private String getOriginalXmlText() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<entries>\n" +
                "\t<entry>\n" +
                "\t\t<field>1</field>\n" +
                "\t</entry>\n" +
                "\t<entry>\n" +
                "\t\t<field>2</field>\n" +
                "\t</entry>\n" +
                "</entries>";
    }

    private String getTransformXmlText() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                "<entries>\r\n" +
                "    <entry field=\"1\"/>\r\n" +
                "    <entry field=\"2\"/>\r\n" +
                "</entries>\r\n";
    }
}