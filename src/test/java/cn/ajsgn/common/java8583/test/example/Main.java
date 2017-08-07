package cn.ajsgn.common.java8583.test.example;

import cn.ajsgn.common.java8583.core.Iso8583Message;
import cn.ajsgn.common.java8583.core.Iso8583MessageFactory;
import cn.ajsgn.common.java8583.quickstart.SingletonFactory;

public class Main {
	
	static String msg = "007960101200006022000000000200603804C020C09811166258091350440751310000681200084324053002106506376258091350440751D20052010000005700000F617874657374332D33303635383130353439393030303331353673586D9C99D3E922260000000000000000080116053088756FB0F2216395";
	
	public static void main(String[] args) {
		Iso8583MessageFactory factory = SingletonFactory.forQuickStart();
		Iso8583Message message = factory.parse(msg);
		
		System.out.println(message.getValue(2).getFieldType().getFieldLength() );
		
		System.out.println(message.toFormatString());
		
		
		
	}
	
}
