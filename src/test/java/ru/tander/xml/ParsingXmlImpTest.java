package ru.tander.xml;

import org.junit.Assert;
import org.junit.Test;

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
    private static final ParsingXml parsingXml = new ParsingXmlImp();

    @Test
    public void createXmlFile() {
        parsingXml.delFile(ORIGINAL_XML_TEST);
        int countAddFields = parsingXml.createXmlFile(FIELDS, ORIGINAL_XML_TEST);
        Assert.assertTrue(countAddFields == FIELDS.length);
        Assert.assertTrue(new File(ORIGINAL_XML_TEST).exists());
    }

    @Test
    public void createXmlFileFieldsNull() throws IOException {
        File file = createFile(ORIGINAL_XML_TEST, "");
        int countAddFields = parsingXml.createXmlFile(null, ORIGINAL_XML_TEST);
        Assert.assertTrue(countAddFields == 0);
        Assert.assertFalse(file.exists());
    }

    @Test
    public void createXmlFilePathNull() {
        int countAddFields = parsingXml.createXmlFile(FIELDS, null);
        Assert.assertTrue(countAddFields == 0);
    }

    @Test
    public void transformXml() throws IOException {
        parsingXml.delFile(TRANSFORM_XML_TEST);
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