package com.ruimin.ifs.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.StringWriter;
@Slf4j
public class XmlPurifyUtil
{
    /**
     * 格式化xml
     * @param xml
     * @return
     */
    public static String purifyXml(String xml)
    {
        Document document = null;
        StringWriter writer=null;
        try
        {
            document = DocumentHelper.parseText(xml);
            OutputFormat xmlFormat =new OutputFormat();
            xmlFormat.setEncoding("gbk");
            xmlFormat.setNewlines(true);
            xmlFormat.setIndent(true);
            xmlFormat.setIndent("  ");

            writer= new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(writer, xmlFormat);
            xmlWriter.write(document);
            xmlWriter.flush();
            xmlWriter.close();
        } catch (Exception e)
        {
            log.error("【{}】格式化异常",xml);
            return "";
        }
        if (writer!=null)
        {
            return writer.toString();
        }
        return "";
    }
}
