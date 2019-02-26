/*
 * Copyright (c) 2019, Ajsgn 杨光 (Ajsgn@foxmail.com).
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

import cn.ajsgn.common.java8583.core.Iso8583Message;
import cn.ajsgn.common.java8583.core.Iso8583MessageFactory;
import cn.ajsgn.common.java8583.quickstart.SingletonFactory;
import cn.ajsgn.common.java8583.util.EncodeUtil;
import cn.ajsgn.common.java8583.util.MacUtil;
import cn.ajsgn.common.java8583.util.PinUtil;

/**
 * <p>真实数据测试演示代码</p>
 * <p>ps：以下代码为线上数据hack而来，其中，修改了卡号，卡片pin码</p>
 * <p>修改内容：</p>
 * <li>1、交易卡号修改为：1234567890123456</li>
 * <li>2、二磁道信息数据修改为：1234567890123456D00000000001111111111F</li>
 * <li>3、卡片交易面修改为：123456</li>
 * @ClassName: DateTest
 * @Description: 真实数据测试演示代码
 * @author Ajsgn@foxmail.com
 * @date 2019年2月26日 下午2:06:55
 */
public class DateTest {
	/*
	 * 卡片号码，模拟号码
	 */
	private static final String CARD_NO = "1234567890123456";
	/*
	 * 卡片密码，模拟密码
	 */
	private static final String CARD_PIN = "123456";
	/*
	 * 二磁道信息，模拟二磁道信息
	 */
	private static final String Track_2_Data = "1234567890123456D00000000001111111111F";
	
	public static void main(String[] args) {
		byte[] result = mtype0200Req();		//发起请求，并获取响应报文
		mtype0200Resp(result);	//处理响应报文
	}

	/* 
	 * 0200，有pin码，查询积分请求
	 */
	private static byte[] mtype0200Req() {
		Iso8583MessageFactory factory = SingletonFactory.forQuickStart();
		Iso8583Message message = new Iso8583Message(factory);
		
		message.setTpdu("6010120000")	//
			.setHeader("602200000000")	//
			.setMti("0200");
		
		message.setValue(2, CARD_NO);
		message.setValue(3, "310000");
		message.setValue(11, "674712");
		message.setValue(12, "232505");
		message.setValue(13, "0528");
		message.setValue(22, "0210");
		message.setValue(25, "65");
		message.setValue(26, "06");
		message.setValue(35, Track_2_Data);
		message.setValue(41, "axtest3-");
		message.setValue(42, "306581054990003");
		message.setValue(49, "156");
		message.setValue(52, MacUtil.DES_3(PinUtil.pinBlockStr(CARD_PIN, CARD_NO), getPik(), 0));	//pin码加密，这里通常会使用加密机执行
		message.setValue(53, "2600000000000000");
		message.setValue(60, "01160528");
		message.setValue(64, MacUtil.Mac_919(getMak(), "0000000000000000", message.getMacBlockString()));	//mac(Message Authentication Code)计算，这里通常会使用加密机执行
		
		byte[] data = message.getBytes();
		return doTrade(data);
	}

	//模拟发起交易
	private static byte[] doTrade(byte[] data) {
		//这里返回报文把长度位移除，通常服务方要自己通过指定的消息长度来读取数据流
//		0066601012000060220000000004007038048002C0801916123456789012345600000000000000050068118623352105290210653938617874657374332D33303635383130353439393030303331353600082216052900162334156811860529284C79DC9A2E50FC
		String dataStr = "601012000060220000000004007038048002C0801916123456789012345600000000000000050068118623352105290210653938617874657374332D33303635383130353439393030303331353600082216052900162334156811860529284C79DC9A2E50FC";
		byte[] result = EncodeUtil.bcd(dataStr);	//这里模拟通信读取到的数据流
		return result;
	}

	private static void mtype0200Resp(byte[] dataWithoutMsgLength) {
		Iso8583MessageFactory factory = SingletonFactory.forQuickStart();
		Iso8583Message message = factory.parseWithoutMsgLength(dataWithoutMsgLength);
		
		String targetMac = message.getMacString();
		String macResult = MacUtil.Mac_919(getMak(), "0000000000000000", message.getMacBlockString());
		if(null != targetMac && true == targetMac.equals(macResult)) {
			System.out.println("mac 校验通过，下面为业务逻辑");
		} else {
			System.out.println("mac 校验未通过");
		}
	}
	
	/*
	 * 发卡方的区域秘钥成份
	 */
	private static String getZmk() {
		//成分1
		String zmkPart1 = "DA3DA723587662DF62DA4FD3858651C4";	//checkValue = A891
		//成分2
		String zmkPart2 = "97E6ECEFD0B00D98B0C701DAEA94A764";	//checkValue = DF29
		return zmk(zmkPart1, zmkPart2);
	}
	
	/*
	 * 对秘钥成份按位异或
	 */
	private static String zmk(String zmkPart1, String zmkPart2) {
		if(null == zmkPart1 || null == zmkPart2)
			return null;
		if(zmkPart1.length() != zmkPart2.length())
			return null;
		
		StringBuilder result = new StringBuilder();
		for(int index = 0; index < zmkPart1.length(); index++) {
			int temp = Integer.valueOf(String.valueOf(zmkPart1.charAt(index)), 16) ^ Integer.valueOf(String.valueOf(zmkPart2.charAt(index)), 16);
			result.append(Integer.toHexString(temp));
		}
		return result.toString().toUpperCase();
	}
	
	private static String getPik() {
		String pikData = "B31C3B99B94E0543B5C13084D47580B7CF9C35EA";	//签到报文中62域数据
		//密文秘钥解密，不通的渠道方，加解密方式可能不同，同时，这个过程可能是由加密机来完成
		return MacUtil.DES_3_32(pikData.substring(0, 32), getZmk(), 1);
	}
	
	private static String getMak() {
		String makData = "AC61F7F11833AEAD03503D29085C047021DD874E";	//签到报文中62域数据
		//密文秘钥解密，不通的渠道方，加解密方式可能不同，同时，这个过程可能是由加密机来完成
		return MacUtil.DES_3_32(makData.substring(0, 32), getZmk(), 1);
	}
	
}
