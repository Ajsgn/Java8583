/*
 * Copyright (c) 2017, Ajsgn 杨光 (Ajsgn@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ajsgn.common.java8583.test.example;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

import cn.ajsgn.common.java8583.core.Iso8583Message;
import cn.ajsgn.common.java8583.core.Iso8583MessageFactory;
import cn.ajsgn.common.java8583.field.Iso8583DataHeader;
import cn.ajsgn.common.java8583.field.Iso8583FieldType;
import cn.ajsgn.common.java8583.field.Iso8583FillBlankStrategy;
import cn.ajsgn.common.java8583.quickstart.SingletonFactory;
import cn.ajsgn.common.java8583.quickstart.special.SpecialField62;
import cn.ajsgn.common.java8583.field.Iso8583FieldType.FieldTypeValue;
import cn.ajsgn.common.java8583.util.EncodeUtil;

/**
 * 快速上手指南
 * @ClassName: QuickStart
 * @Description: 快速上手指南
 * @author Ajsgn@foxmail.com
 * @date 2017年8月3日 下午4:42:35
 */
public class QuickStart {
	
	//某发卡方的签到请求报文
	public static final String MTI0800 = "0041600000000060220000000008000038000000C000120001721705100510353030383030303133303933313030383339383530303800110000017200300003435550";
	//某发卡方的签到响应请求报文
	public static final String MTI0810 = "007960000000006022000000000810003800010AC0001400017210014105100803099988303531303030303030323033303035303038303030313330393331303038333938353030380011000000420030004051DBA323AF60599287F61C7E5A9484AEEF8DE29BD3E614FCB51122D4E9B84E2A608CD9C2A4DAABF2";
	
	public static void main2(String[] args) {
		Iso8583MessageFactory factory = SingletonFactory.forQuickStart();
		factory.setSpecialFieldHandle(62, new SpecialField62());

		Iso8583Message message = new Iso8583Message(factory);
		message.setTpdu("1234567890");
		message.setHeader("123456789012");
		message.setMti("0810");
		message.setValue(62, "51DBA323AF60599287F61C7E5A9484AEEF8DE29BD3E614FCB51122D4E9B84E2A608CD9C2A4DAABF2");
		System.out.println(message.getBytesString());
		
	}
	
	public static void main(String[] args) {
		Iso8583MessageFactory factory = SingletonFactory.forQuickStart();
		factory.setSpecialFieldHandle(62, new SpecialField62());
		Iso8583Message message = factory.parse(MTI0810);
		System.out.println(message.toFormatString());
	}
	
	public static void main3(String[] args) {
		Iso8583MessageFactory factory = chapter3();
		Iso8583Message message = new Iso8583Message(factory);
		message.setTpdu("0000000000")
		   .setHeader("111111111111")
		   .setMti("0200")
		   .setValue(4, "123451234")
		   .setValue(9, "123451234");
		System.out.println(message.toBytesString());
	}
	
	/**
	 * 快速上手指南 —— 入口
	 * 看完下面这个main方法，你将可以完全掌握Java8583
	 * @Title: main
	 * @Description: 快速上手指南 —— 入口
	 * @author g.yang@i-vpoints.com
	 * @date 2017年8月3日 下午8:43:02
	 */
	public static void main1(String[] args) throws Exception {
		//第一章，初识Java8583
		chapter1();
		//第二章，认识ISO8583报文结构与Iso8583DataHeader类
		chapter2();
		//第三章，认识Iso8583MessageFactory以及填充策略Iso8583FillBlankStrategy
		chapter3();
		//第四章，认识Iso8583Message
		chapter4();
		//第五章，再识Iso8583MessageFactory——报文解析
		chapter5();
		//第六章，使用不同的字符编码导致的中文乱码问题
		chapter6();
		//第七章，Iso8583MessageFactory单例的使用
		chapter7();
		//第八章，Java8583的单元测试
		chapter8();
	}

	/*
	 * 认识Java8583中的数据类型：
	 * 从本意上讲，Java8583是没有数据类型概念。
	 * 只是简单的从数据编码类型上做区分为：ASCII编码类型与BCD编码类型。
	 * 其中每种编码类型又在分为定长与变长两种类型。
	 */
	@SuppressWarnings("unused")
	public static void chapter1() {
		// ASCII
		FieldTypeValue character = FieldTypeValue.CHAR;
		FieldTypeValue llvar = FieldTypeValue.LLVAR;
		FieldTypeValue lllvar = FieldTypeValue.LLLVAR;
		FieldTypeValue llllvar = FieldTypeValue.LLLLVAR;
		// BCD
		FieldTypeValue numeric = FieldTypeValue.NUMERIC;
		FieldTypeValue llvarNumeric = FieldTypeValue.LLVAR_NUMERIC;
		FieldTypeValue lllvarNumeric = FieldTypeValue.LLLVAR_NUMERIC;
		FieldTypeValue llllvarNumeric = FieldTypeValue.LLLLVAR_NUMERIC;
	}
	
	/*
	 * ISO8583报文协议相关官方描述，自行走搜索引擎~
	 * 其中报文结构为：dataLength + data 形式，即  报文长度描述+报文体实际内容
	 * 
	 * 报文体data包括：tpdu + dataHeader + 应用数据  三部分
	 * 应用数据  部分包括：mti + bitmap + 域数据
	 * 域数据又分为：64域规范与128域规范
	 * 
	 * 所以，一个8583报文实际应该包含以下结构：
	 * 
	 * 报文总长度 + tpdu + dataHeader + mti + bitmap + N 个域数据
	 * 
	 * 编码处理：
	 * ASCII编码会把一个字符解析成1个字节，两个长度；
	 * BCD编码会把2个字符解析成1个字节，两个长度。
	 * 
	 */
	public static Iso8583DataHeader chapter2() {
		//定义报文头数据格式
		Iso8583DataHeader header = new Iso8583DataHeader(
			// tpdu		BCD编码，5个字节（10个数字长度）
			new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,5),
			// header	BCD编码，6个字节长度
			new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,6),
			// mti		BCD编码，2个字节长度
			new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2),
			// bitmap	BCD编码，8个字节长度
			new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8)
		);
		return header;
	}
	
	/*
	 * Iso8583MessageFactory 
	 * 
	 * 作为Java8583的核心类文件之一，也是其他相关框架的XML替代方案。报文拼装/解析成功与否都取决于factory的配置。
	 * 当然，这样也导致了该对象的配置会略显复杂。
	 * 
	 * 下面会做一个简单的factory配置，若希望看一个比较完整的，可以参考：cn.ajsgn.common.java8583.quickstart.QuickStartFactory
	 * 
	 * 填充策略:
	 * 因为在解析报文时，BCD编码会把两个字符压缩成为1个字节，所以，当报文段落数据为BDC编码，并且长度为奇数时，遍需要去考虑填充位的问题了。
	 * 填充方式分为：左对齐，右填充模式  与   右对齐，左填充模式。
	 * 
	 * 默认使用填充策略：左对齐，右补零
	 * 
	 */
	public static Iso8583MessageFactory chapter3() {
		Iso8583MessageFactory facotry = new Iso8583MessageFactory(2, false, Charset.forName("UTF-8"),chapter2());
		facotry
			   //使用默认填充策略
			   .set(2, new Iso8583FieldType(FieldTypeValue.NUMERIC, 4))
			   //使用左对齐，右补A
			   .set(3, new Iso8583FieldType(FieldTypeValue.NUMERIC, 5).setFillBlankStrategy(Iso8583FillBlankStrategy.rightAppendStrategy('A', false)))
			   //使用右对齐，左补B
			   .set(4, new Iso8583FieldType(FieldTypeValue.LLVAR_NUMERIC, 0).setFillBlankStrategy(Iso8583FillBlankStrategy.leftAppendStrategy('B', false)))
			   //使用左对齐，右补X
			   //超过byte范围的一律表示为FF
			   .set(5, new Iso8583FieldType(FieldTypeValue.LLLVAR_NUMERIC, 0).setFillBlankStrategy(Iso8583FillBlankStrategy.rightAppendStrategy(' ', true)))
			   .set(6, new Iso8583FieldType(FieldTypeValue.LLLLVAR_NUMERIC, 0).setFillBlankStrategy(Iso8583FillBlankStrategy.rightAppendStrategy('D', true)))
			   .set(7, new Iso8583FieldType(FieldTypeValue.CHAR, 4))
			   .set(8, new Iso8583FieldType(FieldTypeValue.LLVAR, 0))
			   .set(9, new Iso8583FieldType(FieldTypeValue.LLLVAR, 0))
			   .set(10, new Iso8583FieldType(FieldTypeValue.LLLLVAR, 0));
		return facotry;
	}
	
	/*
	 * Iso8583Message
	 * 
	 * 作为Java8583的另一个核心类文件，它承载了整个报文数据结构的封装与解析。
	 * 
	 * 与Iso8583MessageFactory比较，Iso8583Message具备构造函数简单，使用方便，易于写单元测试，接口友好等几大特点。
	 * 
	 * 下面是一个简单的报文数据封装。
	 * 
	 * 注意，定长字段，长度为多少字节，就要填多少字节的数据
	 * 
	 */
	public static Iso8583Message chapter4() {
		Iso8583Message message = new Iso8583Message(chapter3());
		message.setTpdu("0000000000")
			   .setHeader("111111111111")
			   .setMti("0200")
			   //12345670
			   .setValue(2,"1234567")
			   //123451234A
			   .setValue(3, "123451234")
			   //05B11111
			   .setValue(4, "11111")
			   //000422FF
			   .setValue(5, "222")
			   //000010123456789E
			   .setValue(6, "123456789")
			   //这里根据设置的字符集不同，会有不同的结果长度与编码结果，都可能不同
			   .setValue(7, "qwer")
			   //0731323334353637
			   .setValue(8, "1234567")
			   //0006414243444546
			   .setValue(9, "ABCDEF")
			   //这里根据设置的字符集不同，会有不同的结果长度与编码结果，都可能不同
			   .setValue(10, "哈哈哈哈~");
		//setXX ... 
//		message.setValue(32, "0123456789");		// NullPointerException 没有对当前索引做配置
		System.out.println(message.getBytesString());
		return message;
	}
	
	/*
	 * Iso8583MessageFactory
	 * 作为Java8583的核心类，除了负责约束报文格式，报文解析也在其职责范围内。
	 * 其中解析方法不考虑重载的情况下有两个：
	 * parse：解析包含报文长度描述的报文内容
	 * 	|--parse(String data)
	 * 		|--通过报文字符串解析报文，其中报文字符串包含报文长度描述
	 * 	|--parse(byte[] data)
	 * 		|--通过报文字节数组解析报文，其中报文字节数组包含报文长度描述
	 * parseWithoutMsgLength：解析不包含报文长度描述的报文内容
	 * 	|--parseWithoutMsgLength(String data)
	 * 		|--通过报文字符串解析报文，其中报文字符串不包含报文长度描述
	 * 	|--parseWithoutMsgLength(byte[] data)
	 * 		|--通过报文字节数组解析报文，其中报文字节数组不包含报文长度描述
	 * 
	 * 通常在报文测试阶段，可以明文看到报文内容与计算出报文内容，所以用parse()方法可能多点，
	 * 开发阶段，报文数据通常都已经读取出来，不在具备长度描述，所以用parseWithoutMsgLength()方法多点
	 */
	public static void chapter5() {
		Iso8583MessageFactory factory = chapter3();
		Iso8583Message message = chapter4();
		
		System.out.println(EncodeUtil.hex(message.getBytes()));
		System.out.println(message.getBytesString());
		
		Iso8583Message m1 = factory.parse(message.getBytes());
		Iso8583Message m2 = factory.parse(message.getBytesString());
		
		System.out.println(m1.compareWith(m2));
		
	}
	
	/*
	 * 如果服务端与客户端编码字符集不统一，则在转换时，就会发生乱码问题。
	 * 
	 * 所以，如果要避免乱码问题，客户端一定要和服务端编码字符集保持一致~！！！
	 * 
	 * 友情提醒，到时候出错，别赖框架！~~
	 * 
	 * 下面是错误示例
	 */
	public static void chapter6() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Iso8583MessageFactory factory = chapter3();
		Iso8583Message message = chapter4();
		
		System.out.println(message.getValue(10).getValue());
		
		Field field = Iso8583MessageFactory.class.getDeclaredField("charset");
		field.setAccessible(true);
		field.set(factory, Charset.forName("GBK"));
		
		Iso8583Message result = factory.parse(message.getBytes());
		System.out.println(result.getValue(10).getValue());
	}
	
	/*
	 * 单例的优点与好处，此处不做多余赘述。
	 * 
	 * 为什么不能直接使用单例？有什么需要注意的？
	 * 都希望服务方的报文字段返回均为相同编码方式，但其实并不是这样，下面代码会列举一个某发卡方的签到报文。
	 * 该签到报文响应中，62域为BCD编码，但在其他响应中为ASCII编码。
	 * 所以，如果不做特殊处理的话，至少需要用到两个facotry。一个用于拼装请求报文，一个用于拼装响应报文。
	 * 下例中用到了一个简单的特殊字段，62域。其中62域在factory中配置为ASCII编码。
	 * 而在 SpecialField62 类中给出判断，如果mti=0810，则使用BCD编码格式。
	 * 
	 * 具体写法，参考 cn.ajsgn.common.java8583.quickstart.special.SpecialField62 源码
	 * 
	 */
	public static void chapter7() {
		Iso8583MessageFactory factory = SingletonFactory.forQuickStart();
		Iso8583Message message0800 = factory.parse(MTI0800);
		System.out.println(message0800.toFormatString());
		
		//62字段的特殊处理逻辑
		factory.setSpecialFieldHandle(62, new SpecialField62());
		
		Iso8583Message message0810 = factory.parse(MTI0810);
		System.out.println(message0810.toFormatString());
		
	}
	
	/*
	 * 看到这里，应该已经完全掌握了Java8583的使用。
	 * 
	 * 这里多说一句，如何保证自己配置的factory是一个正确的factory呢？
	 * 答案很简单，那就是一个报文在解析后调用getBytesString()与原报文字符串的 hex equals 为true
	 * 
	 * 
	 */
	private static void chapter8() {
		Iso8583MessageFactory factory = SingletonFactory.forQuickStart();
		Iso8583Message message0800 = factory.parse(MTI0800);
		System.out.println(message0800.getBytesString());
		System.out.println(MTI0800.equals(message0800.getBytesString()));
		//62字段的特殊处理逻辑
//		factory.setSpecialFieldHandle(62, new SpecialField62());
//		
//		Iso8583Message message0810 = factory.parse(MTI0810);
//		System.out.println(message0810.toFormatString());
//		System.out.println(MTI0810);
//		System.out.println(message0810.getBytesString());
//		System.out.println(MTI0810.equals(message0810.getBytesString()));
	}

}
