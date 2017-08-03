package cn.ajsgn.test.example.main;

import java.nio.charset.Charset;

import cn.ajsgn.common.java8583.core.Iso8583Message;
import cn.ajsgn.common.java8583.core.Iso8583MessageFactory;
import cn.ajsgn.common.java8583.field.FillBlankStrategy;
import cn.ajsgn.common.java8583.field.Iso8583DataHeader;
import cn.ajsgn.common.java8583.field.Iso8583FieldType;
import cn.ajsgn.common.java8583.util.EncodeUtil;

public class Main {
	
	public static void main(String[] args) {
		send();
	}
	
	public static void send() {
		Iso8583MessageFactory factory=getIso8583Factory_zh();
		Iso8583Message message = new Iso8583Message(factory);
		//test用
		message.setHeader("602200000000").setTpdu("6000000000").setMti("0100");
		message.setValue(2, "62284802461254911660");
		message.setValue(3,"330000");
		message.setValue(11,"006198");
		message.setValue(22, "012");
		message.setValue(25, "00");
		message.setValue(41,"11111115");
		message.setValue(42, "103290070111234");
		message.setValue(49, "156");
		message.setValue(60, "01030004000500");
		message.setValue(62, "RB10MPN1113811784067NM06曹朵一ID239750");
		message.setValue(64, "4638323144463545");
		
		System.out.println(message);
		
		System.out.println(EncodeUtil.hex(message.getBytes()));
		
		byte[] bytes = message.getBytes();//将报文转为字节形式用于传输
		// 将十六进制字符串转为十六进制字节数组
		System.out.println("【mac】:"+message.getMacString());

		String reqMsg = EncodeUtil.hex(bytes);
		Iso8583Message parse = factory.parse(reqMsg);
		System.out.println(EncodeUtil.hex(parse.getBytes()));
		
		System.out.println(parse.toFormatString());
		
		String Str64 = parse.getValue(64).getValue();
		boolean flag64= Str64.equals("4638323144463545");
		System.out.println("【64正确与否】"+flag64);
		
//		boolean flag=reqMsg.equals("007F600000000060220000000001006020048000C08015196228480246125491166033000000619801200031313131313131353130333239303037303131313233343135360013010300040005000038524231304D504E313131333831313738343036374E4D3036E69BB9E69CB5E4B88049443233393735304638323144463545");
//		System.out.println("实际发送的报文是否正确："+flag);
	}
	
//	public static void send() {
//		Iso8583Factory factory=getIso8583Factory_zh();
//		Iso8583Message message = new Iso8583Message(factory);
//		//test用
//		message.setHeader("602200000000").setTpdu("6000000000").setMti("0100");
//		message.setValue(2, "62284802461254911660");
//		message.setValue(3,"330000");
//		message.setValue(11,"006198");
//		message.setValue(22, "012");
//		message.setValue(25, "00");
//		message.setValue(41,"11111115");
//		message.setValue(42, "103290070111234");
//		message.setValue(49, "156");
//		message.setValue(60, "01030004000500");
//		message.setValue(62, "RB10MPN1113811784067NM06曹朵一ID239750");
//		message.setValue(64, "4638323144463545");
//		
//		byte[] bytes = message.getBytes();//将报文转为字节形式用于传输
//		// 将十六进制字符串转为十六进制字节数组
//		System.out.println("【mac】:"+message.getMacString());
//		String reqMsg = EncodeUtil.hex(bytes);
//		
//		boolean flag=reqMsg.equals("007F600000000060220000000001006020048000C08015196228480246125491166033000000619801200031313131313131353130333239303037303131313233343135360013010300040005000038524231304D504E313131333831313738343036374E4D3036E69BB9E69CB5E4B88049443233393735304638323144463545");
//		System.out.println("实际发送的报文是否正确："+flag);
//	}
	
	public static Iso8583MessageFactory getIso8583Factory_zh(){//账户验证的报文模板
		Iso8583MessageFactory factory = new Iso8583MessageFactory(2,false,Charset.forName("gbk"));
		Iso8583DataHeader dataHeaderType = new Iso8583DataHeader(
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,5),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,6),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8));
		factory.setDataHeader(dataHeaderType)
		.set( 2, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('0', false)))
		.set( 3, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
		.set( 4, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,6))
		.set(11, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
		.set(12, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
		.set(13, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
		.set(14, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
		.set(15, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
		.set(22, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('0', true)))
		.set(23, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
		.set(25, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,1))
		.set(26, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,1))
		.set(32, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
		.set(35, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
		.set(36, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
		.set(37, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,12))
		.set(38, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,6))
		.set(39, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,2))
		.set(41, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,8))
		.set(42, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,15))
		.set(44, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR,0))
		.set(48, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0))
		.set(49, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,3))
		.set(52, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8))
		.set(53, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8))
		.set(54, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
		.set(55, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
		.set(58, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
		.set(60, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('0', false)))
		.set(61, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0))
		.set(62, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
		.set(63, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
		.set(64, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8));//之前是char，8
		return factory;
	}
	
}
