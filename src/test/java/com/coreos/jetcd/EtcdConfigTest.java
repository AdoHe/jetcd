package com.coreos.jetcd.resolver;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.internal.Yaml;

import java.io.*;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;


public class EtcdConfigTest {

    private Map yamlMap;

    @BeforeClass
    public void testBeforeClass() throws FileNotFoundException, YamlException {
        YamlReader yamlReader = new YamlReader(new FileReader("src/test/java/com/coreos/jetcd/resolver/yamlConfig"));
        yamlMap = (Map)yamlReader.read();
    }

    /**R
     * Test on getting endPoints of LIST type.
     * @throws Exception
     */
    @Test
    public void testGetEndPoints() throws Exception {
        Assert.assertEquals(((List<String>)yamlMap.get("endPoints")).get(0),"www.google.com");
        Assert.assertEquals(((List<String>)yamlMap.get("endPoints")).get(1),"www.facebook.com");

    }

    /**
     * Test on getting a dialTimeout of LONG type.
     * @throws Exception
     */
    @Test
    public void testGetDialTimeout() throws Exception {
        System.out.println((Long.parseLong((String)yamlMap.get("dialTimeout"))));
        Assert.assertEquals((Long.parseLong((String)yamlMap.get("dialTimeout"))),1000);
    }


    @Test
    public void testConfigFromFile() throws Exception {

    }



}