package com.zl.demo.common.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/11/9 20:33
 */
public class JaxpUtil {
    /**
     * @Description 对象转化XML
     * @param obj 待转化对象
     * @throws Exception
     * @return String 组成的字符串
     */
    public static String toXML(Object obj) throws Exception {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        String xml = writer.toString();
        return xml;
    }

    /**
     * @Description XML转化为对象
     * @param xml 传入的XML报文
     * @param valueType 需要返回的cls
     * @throws JAXBException 参数
     * @return T 解析完成对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXML(String xml, Class<T> valueType) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(valueType);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new StringReader(xml));
    }

}
