package cn.ajsgn.test.example.main;

import cn.ajsgn.common.java8583.core.Iso8583Message;
import cn.ajsgn.common.java8583.core.Iso8583MessageFactory;
import cn.ajsgn.common.java8583.quickstart.special.SpecialField62Handler;
import cn.ajsgn.common.java8583.util.EncodeUtil;
import cn.ajsgn.test.example.factory.SingletonBankFactory;

public class MainTest2 {
	
	public static void main(String[] args) {
		//解析部分
		String data = "007960000000006022000000000810003800010AC0001400585517420407110848023310303030313130313732333332303031313131313131353130333239303037303131313233340011000001160030004077264C5EBA2E461248D2A773277A79EA12922DDEC0DF5A74CE24FDB00000000000000000DCF475D3";
		Iso8583MessageFactory factory = SingletonBankFactory.getCIBIso8583Factory();
		factory.setSpecialFieldHandle(62, new SpecialField62Handler());
		Iso8583Message message = factory.parse(data);
		System.out.println(message);
		byte[] bData = message.getBytes();
		String bDataStr = EncodeUtil.hex(bData);
		System.out.println(bDataStr);
		System.out.println(bDataStr.equals(data));
		
		System.out.println(message.getValue(62).getValue());
		
		//生成部分
//		Iso8583Message msg = new Iso8583Message(factory);
//		msg.setHeader("").setMti("").setTpdu("");
//		msg.setValue(2, "")
//			.setValue(3, "")
//			.setValue(4, "")
//			.setValue(5, "")
//			.setValue(6, "")
//			.setValue(7, "");
//		//用于计算mac
//		String macBlock = msg.getMacBlockString();
//		String mac = "";//xxx
//		msg.setValue(64, mac);
//		byte[] bs = msg.getBytes();
		
		// socket.send(bs);
		
	}
	
}
