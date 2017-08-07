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
package cn.ajsgn.common.java8583.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import cn.ajsgn.common.java8583.constant.Iso8583ConstantValue;
import cn.ajsgn.common.java8583.field.Iso8583FillBlankStrategy;
import cn.ajsgn.common.java8583.field.Iso8583Field;
import cn.ajsgn.common.java8583.field.Iso8583FieldType;
import cn.ajsgn.common.java8583.special.SpecialField;
import cn.ajsgn.common.java8583.util.EncodeUtil;
import cn.ajsgn.common.java8583.util.StringUtil;

/**
 * <p>可以表示一个ISO8583报文协议消息的对象</p>
 * @ClassName: Iso8583Message
 * @Description: 可以表示一个ISO8583报文协议消息的对象
 * @author Ajsgn@foxmail.com
 * @date 2017年3月23日 下午6:13:30
 */
public class Iso8583Message {
	
	/**
	 * <p>自身所拥有的一个报文格式工厂</p>
	 */
	private Iso8583MessageFactory factory = null;
	/**
	 * <p>当前报文所对应的一个bitmap 64/128 域规范由本身持有的factory.isBit128()方法决定</p>
	 */
	private byte[] bitmap = null;
	private Map<Integer,Iso8583Field> values = new TreeMap<Integer,Iso8583Field>();
	private String tpdu = "";
	private String header = "";
	private String mti = "";
	
	/**
	 * <p>构造函数，需要一个Iso8583MessageFactory来约束报文解析规范</p>
	 */
	public Iso8583Message(Iso8583MessageFactory factory){
		if(null == factory)
			throw new NullPointerException("required param factory is null");
		this.factory = factory;
		if(factory.isBit128()){
			bitmap = new byte[128];
			bitmap[0] = 1;
		}else{
			bitmap = new byte[64];
			bitmap[0] = 0;
		}
	}
	
	/**
	 * <p>向报文中插入值，返回自身对象</p>
	 * <p>当出现以下情况时，会忽略操作，直接返回，而不会抛出异常</p>
	 * <ol>
	 *   <li>字段索引小于2时；</li>
	 *   <li>使用64域规范时，字段索引大于64；</li>
	 *   <li>使用128域规范时，字段索引大于128；</li>
	 * </ol>
	 * @Title: setValue
	 * @Description: 向报文中插入值
	 * @param index 域字段索引
	 * @param value 域字段值
	 * @return Iso8583Message
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午10:37:48
	 */
	public Iso8583Message setValue(int index,String value){
		if(index<2)
			return this;
		if(true == factory.isBit128() && index >128)
			return this;
		if(false == factory.isBit128() && index >64)
			return this;
		Iso8583FieldType type = factory.getFieldType(index);
		//将数据填入map，已处理填充位数据
		values.put(index,new Iso8583Field(index,getFieldValue(value, type, true),type));
		//位图标记
		bitmap[index-1] = 1;
		return this;
	}
	
	/**
	 * <p>获取报文中的某个域的值</p>
	 * <p>不关注填充内容，获取到的结果值中不包含填充内容</p>
	 * @Title: getValue
	 * @Description: 获取报文中的某个域的值
	 * @param index 域的索引值
	 * @return Iso8583IsoField
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午10:50:59
	 */
	public Iso8583Field getValue(int index){
		return values.get(index);
	}
	
	/**
	 * <p>获取报文中的某个域的值</p>
	 * <p>关注填充内容，获取到的值中包含填充值</p>
	 * @Title: getValue
	 * @Description: 获取报文中的某个域的值
	 * @param index 域的索引值
	 * @param filledValue 是否要包含填充位数据
	 * @return Iso8583Field
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月27日 下午12:33:53
	 */
	public Iso8583Field getValue(int index,boolean filledValue){
		Iso8583Field field = getValue(index);
		Iso8583FieldType type = factory.getFieldType(index);
		String value = getFieldValue(field.getValue(), type, filledValue);
		return new Iso8583Field(index, value, type);
	}
	
	/**
	 * <p>返回bitmap的位图内容</p>
	 * <p>返回值示例：0110000000111100000000001000000100001010110100001000110000010001</p>
	 * @Title: getBitmapBitString
	 * @Description: 返回bitmap的位图内容
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:08:16
	 */
	public String getBitmapBitString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<bitmap.length;i++){
			sb.append(bitmap[i]);
		}
		return sb.toString();
	}
	
	/**
	 * <p>获取bitmap的字节数组内容</p>
	 * @Title: getBitmapBytes
	 * @Description: 获取bitmap的字节数组内容
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:10:00
	 */
	public byte[] getBitmapBytes(){
		return EncodeUtil.binary(getBitmapBitString());
	}
	
	/**
	 * <p>获取bitmap字节数组的字符串表现形式</p>
	 * <p>返回示例：603C00810AD08C11</p>
	 * @Title: getBitmapString
	 * @Description: 获取bitmap字节数组的字符串表现形式
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:12:15
	 */
	public String getBitmapString(){
		return EncodeUtil.hex(getBitmapBytes());
	}
	
	/**
	 * <p>格式化消息输出</p>
	 * <p>建议用于开发阶段调试，因为其打印内容未做掩码，为纯明文内容，不安全</p>
	 * @Title: toFormatString
	 * @Description: 格式化消息输出
	 * @return String 格式化输出
	 * @author Ajsgn@foxmail.com
	 * @date 2017年7月25日 下午3:08:44
	 */
	public String toFormatString(){
		StringBuilder sb = new StringBuilder();
		String format = "%s\t:%s";
		sb.append("\n");
		sb.append(String.format(format, Iso8583ConstantValue.TPDU.getValue(),getTpdu()));
		sb.append("\n");
		sb.append(String.format(format, Iso8583ConstantValue.HEADER.getValue(),getHeader()));
		sb.append("\n");
		sb.append(String.format(format, Iso8583ConstantValue.MTI.getValue(),getMti()));
		sb.append("\n");
		sb.append(String.format(format, Iso8583ConstantValue.BITMAP.getValue(),getBitmapString()));
		sb.append("\n");
		for(Map.Entry<Integer,Iso8583Field> entry:values.entrySet()){
			Iso8583Field field = entry.getValue();
			sb.append(String.format(format, field.getIndex(),field.getValue()));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * <p>获取当前报文的字符串表现形式</p>
	 * @Title: toBytesString
	 * @Description: 获取当前报文的字符串表现形式
	 * @return String 当前报文的字符串表现形式
	 * @author Ajsgn@foxmail.com
	 * @date 2017年7月26日 上午9:07:55
	 */
	public String toBytesString(){
		return EncodeUtil.hex(getBytes());
	}
	
	/**
	 * <p>解析报文协议，返回字节数组，用于应用间的消息的传输</p>
	 * @Title: getBytes
	 * @Description: 解析报文协议，返回字节数组，用于应用间的消息的传输
	 * @return byte[] 用于传输的报文包
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:13:39
	 */
	public byte[] getBytes(){
		//结果返回数据内容
		byte[] resultContent = new byte[0];
		try{
			//创建一个不用关闭的输出流，用于最后的byte[]转换，不用维护数组的pos
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//写入tpdu
			baos.write(getTpduBytes());
			//写入header
			baos.write(getHeaderBytes());
			//写入mti+bitmap+8583报文数据（设置有校验位，则包含，没设置校验位，则不包含）
			baos.write(getMacBlock());
			//写入校验域
			baos.write(getMac());
			//计算报文整体长度
			String size = StringUtil.repeat("00", factory.getMsgLength())+Integer.toHexString(baos.size());
			size = size.substring(size.length() - factory.getMsgLength() * 2);
			byte[] bSize = EncodeUtil.bcd(size);
			//准备最终报文数据
			ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
			//写入报文长度
			resultStream.write(bSize);
			//写入报文信息
			baos.writeTo(resultStream);
			resultContent = resultStream.toByteArray();
		}catch(IOException e){
			e.printStackTrace();
		}
		return resultContent;
	}
	
	/**
	 * 获取当前报文的完整字符串表示形式
	 * @Title: getBytesString
	 * @Description: 获取当前报文的完整字符串表示形式
	 * @return String 完整字符串表示形式
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月3日 下午7:52:01
	 */
	public String getBytesString(){
		return EncodeUtil.hex(getBytes());
	}
	
	/**
	 * <p>获取用于计算mac的macBlock的字符串表示</p>
	 * <p>macBlock : mti+bitmap+data(除去校验位的8583报文数据)</p>
	 * @Title: getMacBlockString
	 * @Description: 获取用于计算mac的macBlock的字符串表示
	 * @return String macBlock的字符串格式数据
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:35:49
	 */
	public String getMacBlockString(){
		return EncodeUtil.hex(getMacBlock());
	}
	
	/**
	 * <p>获取用于计算mac的macBlock的字节数组表示</p>
	 * <p>macBlock : mti+bitmap+data(出去校验位的8583报文数据)</p>
	 * @Title: getMacBlock
	 * @Description: 获取用于计算mac的macBlock的字节数组表示
	 * @return byte[] macBlock的字节数组格式数据
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:37:11
	 */
	public byte[] getMacBlock(){
		byte[] resultContent = new byte[0];
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//写入mit
			baos.write(getMtiBytes());
			//写入bitmap
			baos.write(getBitmapBytes());
			//循环写入字段信息
			for(Map.Entry<Integer,Iso8583Field> entry:values.entrySet()){
				//不写入校验域
				if(entry.getKey() != bitmap.length){
					Iso8583Field field = entry.getValue();
					baos.write(parse(field.getValue(), field.getFieldType()));
				}
			}
			resultContent = baos.toByteArray();
		}catch(IOException e){
			e.printStackTrace();
		}
		return resultContent;
	}
	
	/**
	 * <p>获取校验域的字符串形式值</p>
	 * @Title: getMacString
	 * @Description: 获取校验域的字符串形式值
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:48:26
	 */
	public String getMacString(){
		return EncodeUtil.hex(getMac());
	}
	
	/**
	 * <p>获取校验域的字节数组格式</p>
	 * @Title: getMac
	 * @Description: 获取校验域的字节数组格式
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:52:26
	 */
	public byte[] getMac(){
		byte[] resultContent = new byte[0];
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//取最后一个域
			Iso8583Field field = values.get(bitmap.length);
			if(null != field){
				baos.write(parse(field.getValue(), field.getFieldType()));
				resultContent = baos.toByteArray();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return resultContent;
	}
	
	/**
	 * <p>获取tpdu的字节数组格式</p>
	 * @Title: getTpduBytes
	 * @Description: 获取tpdu的字节数组格式
	 * @return byte[] 获取tpdu的字节数组格式
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:52:57
	 */
	public byte[] getTpduBytes(){
		Iso8583FieldType type = factory.getTpduType();
		byte[] content = parse(this.tpdu,type);
		return content;
	}
	
	/**
	 * <p>获取header的字节数组格式</p>
	 * @Title: getHeaderBytes
	 * @Description: 获取header的字节数组格式
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:56:30
	 */
	public byte[] getHeaderBytes(){
		Iso8583FieldType type = factory.getHeaderType();
		byte[] content = parse(this.header,type);
		return content;
	}
	
	/**
	 * <p>获取mti的字节数组格式</p>
	 * @Title: getMtiBytes
	 * @Description: 获取mti的字节数组格式
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:57:10
	 */
	public byte[] getMtiBytes(){
		Iso8583FieldType type = factory.getMtiType();
		byte[] content = parse(this.mti,type);
		return content;
	}
	
	/**
	 * <p>解析字段内容</p>
	 * @Title: parse
	 * @Description: 解析字段内容
	 * @param value 当前字段的值
	 * @param type 当前字段的类型
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:57:35
	 */
	private byte[] parse(String value, Iso8583FieldType type) {
		byte[] result = new byte[0];
		if(null != value){
			//取当前字段对应的特殊处理器
			SpecialField fieldHandle = this.factory.getSpecialFieldHandler(type.getFieldIndex());
			if(null != fieldHandle){
				Iso8583FieldType newType = new Iso8583FieldType(fieldHandle.forGetBytes(type, getMti()),type.getFieldLength());
				newType.setFieldIndex(type.getFieldIndex());
				newType.setFillBlankStrategy(type.getFillBlankStrategy());
//				//使用例外的数据类型
				type = newType;
			}
			//原执行逻辑
			switch (type.getFieldTypeValue()) {
				case CHAR:{
					if(value.length() > type.getFieldLength()){
						value = value.substring(0, type.getFieldLength());
					}
					result = value.getBytes(this.factory.getCharset());
					break;
				}
				case BINARY:{
					//TODO 保持16进制格式  --不确定
					result = EncodeUtil.bcd(value);
					break;
				}
				case NUMERIC:{
					if(value.length() > type.getFieldLength() * 2){
						value = value.substring(0, type.getFieldLength() * 2);
					}
					//如果长度不为2的倍数，则需要进行补位操作
					if(0 != value.length()%2){
						char c = type.getFillBlankStrategy().getValue();
						value = value + String.valueOf(c);
					}
					result = EncodeUtil.bcd(value);
					break;
				}
				case LLVAR:{
					result = variableLengthParse(value,1,true);
					break;
				}
				case LLLVAR:{
					result = variableLengthParse(value,2,true);
					break;
				}
				case LLLLVAR:{
					result = variableLengthParse(value,3,true);
					break;
				}
				case LLVAR_NUMERIC:{
					result = numericVariableLengthParse(value,1,false,type);
					break;
				}
				case LLLVAR_NUMERIC:{
					result = numericVariableLengthParse(value,2,false,type);
					break;
				}
				case LLLLVAR_NUMERIC:{
					result = numericVariableLengthParse(value,3,false,type);
					break;
				}
				default:{
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * <p>针对LLVAR,LLLVAR,LLLLVAR的变长字段解析为byte[]</p>
	 * @Title: variableLengthParse
	 * @Description: 针对LLVAR,LLLVAR,LLLLVAR的变长字段解析为byte[]
	 * @param value 当前字段的值
	 * @param dataLength 消息长度
	 * @param byteLength 是否为字节长度
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:58:34
	 */
	private byte[] variableLengthParse(String value,int dataLength,boolean byteLength){
		//长度填充
		String fieldLength = StringUtil.repeat("00", dataLength) + value.getBytes(this.factory.getCharset()).length;
		return variableLengthParse(value,fieldLength,dataLength,byteLength);
	}
	
	/**
	 * <p>针对LLVAR_NUMERIC,LLLVAR_NUMERIC的变长字段解析为byte[]</p>
	 * <p>类型LLVAR_NUMERIC,LLLVAR_NUMERIC比类型LLVAR_NUMERIC,LLLVAR_NUMERIC多一个填充内容策略</p>
	 * @Title: numericVariableLengthParse
	 * @Description: 针对LLVAR_NUMERIC,LLLVAR_NUMERIC的变长字段解析为byte[]
	 * @param value 原报文内容（不区分是否包含填充位内容）
	 * @param dataLength 消息长度
	 * @param byteLength 是否为字节长度
	 * @param fillBlankStrategy 填充策略
	 * @return byte[]
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月27日 上午11:42:01
	 */
	private byte[] numericVariableLengthParse(String value,int dataLength,boolean byteLength,Iso8583FieldType type){
		String fieldLength = StringUtil.repeat("00", dataLength) + (type.getFillBlankStrategy().isLengthWithBlank() ? getFieldValue(value,type,true).length() : getFieldValue(value,type,false).length());
		value = getFieldValue(value, type, true);
		return variableLengthParse(value,fieldLength,dataLength,byteLength);
	}
	
	/**
	 * 获取某一个字段值的填充/不填充结果
	 * @Title: getField
	 * @Description: 获取某一个字段值的填充/不填充结果
	 * @param value 原报文内容
	 * @param type 当前字段对应的字段类型
	 * @param filledValue 读取填充值：true，读取有填充的内容，false，读取未填充的值
	 * @return String 报文对应的内容
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月27日 上午11:59:02
	 */
	private String getFieldValue(String value,Iso8583FieldType type,boolean filledValue){
		if(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC == type.getFieldTypeValue() 
			|| Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC == type.getFieldTypeValue()
			|| Iso8583FieldType.FieldTypeValue.LLLLVAR_NUMERIC == type.getFieldTypeValue()){
			Iso8583FillBlankStrategy fillBlankStrategy = type.getFillBlankStrategy();
			//用于填充的填充字符
			String fillChar = String.valueOf(fillBlankStrategy.getValue());
			//获取补齐结果与非补齐结果报文内容
			String filledResult = "";			//填充的结果
			String noFillResult = "";		//没有填充的结果
			if(0 == value.length() % 2){
				//如果为偶数，则不需要填充
				if(fillBlankStrategy.isLeftAppend()){
					//如果左填充
					if(value.startsWith(fillChar)){
						//以填充字符开头
						filledResult = value;
						noFillResult = value.substring(1);
					}else{
						//不需要做填充处理
						filledResult = value;
						noFillResult = value;
					}
				}else{
					//如果右填充
					if(value.endsWith(fillChar)){
						//以填充字符结尾
						filledResult = value;
						noFillResult = value.substring(0,value.length()-1);
					}else{
						//不需要做填充处理
						filledResult = value;
						noFillResult = value;
					}
				}
			}else{
				//如果长度不为偶数，则需要填充
				//判断对齐放向
				filledResult = fillBlankStrategy.isLeftAppend() ? fillChar + value : value + fillChar;
				noFillResult = value;
			}
			return filledValue ? filledResult : noFillResult;
		}else{
			return value;
		}
	}
	
	/**
	 * <p>边长字段报文解析拼装</p>
	 * @Title: variableLengthParse
	 * @Description: 边长字段报文解析拼装
	 * @param value 参与拼接的报文内容体
	 * @param fieldLength 经过计算之后的hex(长度值)
	 * @param dataLength 报文长度位的字节长度
	 * @param byteLength 报文体长度是否为字节长度
	 * @return byte[]
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月27日 上午11:38:32
	 */
	private byte[] variableLengthParse(String value,String fieldLength,int dataLength,boolean byteLength){
		//计算bcd长度位
		byte[] bLength = EncodeUtil.bcd(fieldLength.substring(fieldLength.length()-dataLength*2));
		//获取报文的字节数组
		byte[] bContent = byteLength?value.getBytes(this.factory.getCharset()):EncodeUtil.bcd(value);
		//数据拼接 length+data
		byte[] content = new byte[bLength.length+bContent.length];
		System.arraycopy(bLength, 0, content, 0, bLength.length);
		System.arraycopy(bContent, 0, content, bLength.length, bContent.length);
		return content;
	}

	
	// >> setter & getter
	/**
	 * <p>getter</p>
	 */
	public String getTpdu() {
		return tpdu;
	}
	/**
	 * <p>getter</p>
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * <p>getter</p>
	 */
	public String getMti() {
		return mti;
	}
	/**
	 * <p>setter</p>
	 */
	public Iso8583Message setTpdu(String tpdu) {
		this.tpdu = tpdu;
		return this;
	}
	/**
	 * <p>setter</p>
	 */
	public Iso8583Message setHeader(String header) {
		this.header = header;
		return this;
	}
	/**
	 * <p>setter</p>
	 */
	public Iso8583Message setMti(String mti) {
		this.mti = mti;
		return this;
	}
	
	/**
	 * 比较两个Iso8583Message对象是否一样
	 * @Title: compareWith
	 * @Description: 比较两个Iso8583Message对象是否一样
	 * @param message 目标对象
	 * @return boolean 比较结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月3日 下午7:58:54
	 */
	public boolean compareWith(Iso8583Message message){
		if(null == message){
			return false;
		}
		if (this == message) {
            return true;
        }
		return this.getBytesString().equals(message.getBytesString());
	}
	
}
