package com.ngandroid.demo.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

import com.ngandroid.demo.content.UserEntry;

public class XMLDomUtil {
	private static final String TAG = "JustDemo XMLDomUtil.java";
	
	/**
	 * <p>Title: getUserEntry</p>
	 * <p>Description: 用户登陆后获取用户数据</p>
	 * @param inputStream
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public UserEntry getUserEntry(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException{   
	    if(inputStream == null){
	        return null;
	    }
	    UserEntry user = new UserEntry();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
        DocumentBuilder builder = factory.newDocumentBuilder();   
        Document document = builder.parse(inputStream);   
        Element element = document.getDocumentElement();   
        NodeList resultNodes = element.getElementsByTagName("result");
        Element result = (Element)resultNodes.item(0);
        //如果返回的result节点为true则表示请求的成功，即登陆成功
        if("true".equals(result.getTextContent())){
            user.hasResult = true;
        }else{
            user.hasResult = false;
            return user;
        }
        Node userNode = element.getElementsByTagName("data").item(1); 
        Log.v(TAG, "nodename:"+userNode.getTextContent());
        for(Node node=userNode.getFirstChild();node!=null;node=node.getNextSibling()){
            Log.v(TAG, node.getTextContent());
            if("uid".equals(node.getNodeName())){
                user.uid = Integer.parseInt(node.getTextContent());
            }else if("nickname".equals(node.getNodeName())){
                user.nickname = node.getTextContent();
            }else if("expiretime".equals(node.getNodeName())){
                user.expiretime = Integer.parseInt(node.getTextContent());
            }else if("uid".equals(node.getNodeName())){
                user.email = node.getTextContent();
            }
        }
        return user;
    } 
	
}
