package com.zhanglf;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class CxfClientTest {
	public static void main(String[] args) throws Exception {
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		//通过wsdl服务描述文件创建客户端工厂。
		Client client = factory.createClient("http://localhost:9999/TestWebService?wsdl");
		//尝试使用不带?wsdl的地址
		//Client client = factory.createClient("http://localhost:8080/springCXFWebserviceDemo01/service/HelloWorldService");
		//invoke(
		//String operationName:要调用的方法名
		//Object... params)：方法的入参。可以是多个。
		Object[] objs = client.invoke("sayHello", "阿福");
		//invoke方法是默认返回Object[]数组。取出数组的第一位值得值就是返回值。
		System.out.println(objs[0].toString());
	}
}
