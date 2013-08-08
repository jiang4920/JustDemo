package com.ngandroid.demo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

import com.ngandroid.demo.content.ErrorResponse;
import com.ngandroid.demo.content.Response;
import com.ngandroid.demo.content.UserResponse;

public class XMLDomUtil {
	private static final String TAG = "JustDemo XMLDomUtil.java";

	/**
	 * <p>
	 * Title: getUserEntry
	 * </p>
	 * <p>
	 * Description: 用户登陆后获取用户数据
	 * </p>
	 * 
	 * @param inputStream
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public UserResponse getUserEntry(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		if (inputStream == null) {
			return null;
		}
		UserResponse user = new UserResponse();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		Element element = document.getDocumentElement();
		NodeList resultNodes = element.getElementsByTagName("result");
		Element result = (Element) resultNodes.item(0);
		// 如果返回的result节点为true则表示请求的成功，即登陆成功
		if ("true".equals(result.getTextContent())) {
			user.hasResult = true;
		} else {
			user.hasResult = false;
			return user;
		}
		Node userNode = element.getElementsByTagName("data").item(1);
		Log.v(TAG, "nodename:" + userNode.getTextContent());
		for (Node node = userNode.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			Log.v(TAG, node.getTextContent());
			if ("uid".equals(node.getNodeName())) {
				user.uid = Integer.parseInt(node.getTextContent());
			} else if ("nickname".equals(node.getNodeName())) {
				user.nickname = node.getTextContent();
			} else if ("expiretime".equals(node.getNodeName())) {
				user.expiretime = Integer.parseInt(node.getTextContent());
			} else if ("email".equals(node.getNodeName())) {
				user.email = node.getTextContent();
			}
		}
		return user;
	}

	/**
	 * <p>Title: parseXml</p>
	 * <p>Description: 解析XML</p>
	 * @param response 传递进来一个Response，用于接收和处理XML中数据
	 * @param is 服务器返回的输入流
	 * @return
	 */
	public Response parseXml(Response response, InputStream is) {
		SAXReader reader = new SAXReader();
		try {
			org.dom4j.Document document = reader.read(is);
			Log.v(TAG, document.asXML());
			if ("true".equals(document.selectSingleNode("/data/result")
					.getText())) {
				return response.parse(document);
			} else {
				ErrorResponse error = new ErrorResponse();
				error.setSeason(document.selectSingleNode("/data/reason/item")
						.getText());
				return error;
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
