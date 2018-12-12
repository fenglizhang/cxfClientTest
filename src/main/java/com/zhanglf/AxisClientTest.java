package com.zhanglf;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

/**
 * 
 说明：address="http://localhost:8080/HelloWorld/services/user?wsdl"
 * http://localhost:8080/HelloWorld：工程访问路径，
 * /services：此路径在web.xml里面配置cxf拦截器前置访问路径
 * /user：此路径在服务端的applicationContext.xml里配置的，要暴露给外部调用的接口，address:请求路径
 * 
 * @author Administrator
 * 
 */
public class AxisClientTest {

	public static void main(String[] args) throws Exception {
		// namespaceURI
		// 为：一般可以认为是<wsdl:definitions>中的targetNamespace的值，最好还是<wsdl:definitions>标签下的
		// xmlns:tns的值为准。
		// 也是webservice接口的类上注解的targetNamespace,效果如同spring中的requestMapping，用来区分请求定位到具体的那个接口。
		// 这里的命名空间对应的是接口上面的targetNamespace,而不是实现类上面的。故通过wsdl中的<wsdl:definitions
		// 里面的targetNamespace是不准的，应该以<wsdl:import 中的
		// namespace为准或者<wsdl:definitions>标签下的 xmlns:tns的值为准
		String nameSpaceURI = "com.serviceTargetName";
		// 指出service所在URL,这个有没有?wsdl均能正确访问。。。,这里区别于cxf，cxf是不能少这个?wsdl
		String publishUrl = "http://localhost:8080/springCXFWebserviceDemo01/service/HelloWorldService?wsdl";
		// 创建一个服务(service)调用(call)
		Service service = new Service();
		// 通过service创建call对象
		Call call = (Call) service.createCall();
		// 设置webservice接口地址
		call.setTargetEndpointAddress(new URL(publishUrl));
		// 你需要远程调用的方法new QName(定位类的targetnamespace,定位方法的方法名)
		/**
		 * call.setOperationName(new QName("serviceTargetName", "sayHello"));
		 * 方法中的QName方法的入参说明： new QName( String
		 * namespaceURI-定位接口的命名空间：接口注解targetnamespace的值或者wsdl文件
		 * <wsdl:definitions中的xmlns
		 * :tns="com.serviceTargetName"来锁定targetnamespace的值， 这里不能用wsdl文件<wsdl:
		 * definitions中的targetNamespace来确定值的原因在于这里的值来源与接口实现类上的targetNamespace注解的值
		 * 。如果你接口的实现类中的targetNamespace和接口的不一样，岂不是搞错了。 String
		 * localPart-接口下定位方法的方法名
		 * :就是这里的抽象方法sayHello方法名，或者wsdl文件<wsdl:binding标签下<wsdl:operation
		 * name="sayHello"中name的值。 )
		 */
		call.setOperationName(new QName(nameSpaceURI, "sayHello"));
		
		// 方法参数，如果没有参数请无视。这里如果接口没有用@WebParam(name = "name"),则会报错：Unmarshalling
		// Error: 意外的元素 (uri:"", local:"name")。所需元素为<{}arg0>
		call.addParameter("parameterName", XMLType.XSD_STRING, ParameterMode.IN);
		// call.addParameter(new QName(soapaction,"xxxx"), XMLType.XSD_STRING,
		// ParameterMode.IN);
		// 设置返回类型，一般用string接收
		call.setReturnType(XMLType.XSD_STRING);
		// 给方法传递参数，并且调用方法
		String name = "zhanglifeng";
		String temp = getXml(name);
		// 这里的obj｛｝是放入几个入参，完全由service提供的接口方法的入参决定，且顺序和你存放的顺序一致！一般入参为String类型的xml报文，回参也是xml报文。
		Object[] obj = new Object[] { temp };
		String result = (String) call.invoke(obj);

		System.out.println(result);
	}

	private static String getXml(String name) {
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<userBean>");
		sb.append("<userName>" + name + "</userName>");
		sb.append("</userBean>");
		return sb.toString();

	}
}
