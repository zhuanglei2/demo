/**
 * Copyright (C),2018-2019
 * FileName: XmlParserUtils
 * Author:   liujian
 * Date:     2019/10/18 4:56 PM
 * Description: xml格式数据解析工具
 * History:
 * <author>       <time>        <version>       <desc>
 * 作者           修改时间         版本号          描述
 */

package com.zl.demo.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class XmlParserUtils {

    /**
     * 把数据对象转化为XML格式字符串
     * @param xmlObj 待转化数据对象
     * @return XML格式字符串
     */
    public static String getStringFromObject(final Object xmlObj) {
        final XStream xStream = new XStream(new DomDriver("UTF-8",
                new XmlFriendlyNameCoder("-_", "_")));
        xStream.processAnnotations(xmlObj.getClass());
        return xStream.toXML(xmlObj);
    }

    /**
     * 把XML格式字符串转换为数据对象
     * @param xmlStr XML格式字符串
     * @param clazz 数据对象类型
     * @return 数据对象
     */
    public static Object getObjectFromString(final String xmlStr, final Class<?> clazz) {
        final XStream xStream = new XStream(new DomDriver("UTF-8",
                new XmlFriendlyNameCoder("-_", "_")));
        xStream.processAnnotations(clazz);
        xStream.ignoreUnknownElements();
        return xStream.fromXML(xmlStr);
    }

    /**
     * 把XML格式字符串解析为键值对集合
     * @param xmlData XML格式字符串
     * @return 键值对集合
     * @throws ParserConfigurationException {@link ParserConfigurationException}
     * @throws SAXException {@link SAXException}
     * @throws IOException {@link IOException}
     */
    public static Map<String, Object> getMapFromXML(String xmlData) throws ParserConfigurationException, SAXException, IOException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 防止XXE攻击
        String FEATURE = null;
        FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
        factory.setFeature(FEATURE, true);
        FEATURE = "http://xml.org/sax/features/external-general-entities";
        factory.setFeature(FEATURE, false);
        FEATURE = "http://xml.org/sax/features/external-parameter-entities";
        factory.setFeature(FEATURE, false);
        FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
        factory.setFeature(FEATURE, false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final InputStream is = getStream(xmlData);
        final Document doc = builder.parse(is);
        final NodeList allNodes = doc.getFirstChild().getChildNodes();
        final Map<String, Object> dataMap = new HashMap<String, Object>();
        Node node;
        for (int i = 0, len = allNodes.getLength(); i < len; i++) {
            node = allNodes.item(i);
            if (node instanceof Element) {
                dataMap.put(node.getNodeName(), node.getTextContent());
            }
        }
        return dataMap;
    }

    /**
     * 把字符串转为输入流
     * @param inputStr 字符串
     * @return 输入流
     */
    private static InputStream getStream(final String inputStr) {
        ByteArrayInputStream bis = null;
        if (isNotBlank(inputStr)) {
            bis = new ByteArrayInputStream(inputStr.getBytes());
        }
        return bis;
    }
    
}
