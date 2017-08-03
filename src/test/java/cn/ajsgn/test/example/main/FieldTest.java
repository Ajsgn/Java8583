package cn.ajsgn.test.example.main;

import cn.ajsgn.common.java8583.core.Iso8583Message;
import cn.ajsgn.common.java8583.core.Iso8583MessageFactory;
import cn.ajsgn.common.java8583.quickstart.SingletonBankFactory;

public class FieldTest {
	
	public static void main(String[] args) {
		Iso8583MessageFactory factory = SingletonBankFactory.getTestIso8583Factory();
		
		Iso8583Message message = new Iso8583Message(factory);
		message.setTpdu("6022000000");
		message.setHeader("602200000000");
		message.setMti("0810");
		
//		message.setValue(2, "012345678901234");
		
		message.setValue(62, "1122334455667788990");
		
		message.setValue(64, "ABCDEF0123456789");

		String fmt = message.toBytesString();
		System.out.println(fmt);
		System.out.println(message.toFormatString());
		
		message = factory.parse(fmt);
		
		System.out.println(message.toFormatString());
		
		System.out.println(message.toBytesString());
//		System.out.println(message.getValue(62).getValue());
		
	}
	
}
