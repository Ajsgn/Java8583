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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import cn.ajsgn.common.java8583.constant.Iso8583ConstantValue;
import cn.ajsgn.common.java8583.field.Iso8583DataHeader;
import cn.ajsgn.common.java8583.field.Iso8583FieldType;
import cn.ajsgn.common.java8583.special.SpecialFieldHandler;
import cn.ajsgn.common.java8583.util.EncodeUtil;

/**
 * <p>8583报文格式工厂，通过该类来约束一个8583报文各个字段的类型以及处理方式</p>
 * <p>通常对于一家机构而言，其某一字段的类型在该机构的所有请求中的格式都会是一定的，所以该类对象可以做一个单例模式处理</p>
 * <p>如果真的出现有不一样的形式（例如62域，签到时为Numeric，交易时为ASCII），有两种推荐处理方式：</p>
 * <li>1、简单暴力，创建多个factory。这样做的话，相当于J8583与Simple8583的多package方式，但是要麻烦与以上两个框架，因为要做使用逻辑的管理</li>
 * <li>2、使用{@link SpecialFieldHandle}接口。向{@link Iso8583Factory}中注册特殊字段处理逻辑，以保证facotry的单例特性。</li>
 * @ClassName: Iso8583MessageFactory
 * @Description: 8583报文工厂。通过该类来约束一个8583报文各个字段的类型和处理方式以及解析生成{@link Iso8583Message}对象。
 * @author Ajsgn@foxmail.com
 * @date 2017年3月23日 下午12:46:48
 */
public class Iso8583MessageFactory {
	
	/**
	 * <p>定义报文长度的字节数</p>
	 * <p>约束报文通过几个字节长度来表示报文体长度</p>
	 * <p>默认使用2个字节长度表示报文体长度</p>
	 */
	private int msgLength = 2;
	/**
	 * <p>约束报文规范：64域？128域？</p>
	 * <p>默认使用64域格式</p>
	 */
	private boolean bit128 = false;
	/**
	 * <p>默认编码字符集</p>
	 * <p>默认使用UTF-8编码格式</p>
	 */
	private Charset charset = Charset.forName("UTF-8");
	/**
	 * <p>用于保存字段类型的集合</p>
	 * <p>相当于是一个报文模板集合</p>
	 */
	private Map<String,Iso8583FieldType> dataFactory = new TreeMap<String,Iso8583FieldType>();
	/**
	 * <p>用于保存需要特殊处理的字段的值</p>
	 * <p>特殊字段处理策略集合</p>
	 */
	private Map<String,SpecialFieldHandler> specialFieldHandlerMap = new HashMap<String,SpecialFieldHandler>();

	/**
	 * <p>构造函数</p>
	 * @param msgLength： 报文通过几个字节来表示报文体长度
	 * @param bit128： 是否使用128域的8583报文规范。默认为false，使用64位域规范
	 * @param charset： ASCII编码字段的编码类型
	 */
	public Iso8583MessageFactory(int msgLength,boolean bit128,Charset charset){
		this.msgLength = msgLength;
		this.bit128 = bit128;
		this.charset = charset;
	}
	
	/**
	 * <p>设置消息头格式类型</p>
	 * @Title: setDataHeader
	 * @Description: 设置消息头格式类型
	 * @param headerType 消息头格式类型，包含：tpdu,header,mti,bitmap
	 * @return Iso8583Factory
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午1:08:56
	 */
	public Iso8583MessageFactory setDataHeader(Iso8583DataHeader header){
		//设置tpdu的消息格式
		this.setTpduType(header.getTpduType());
		//设置header的消息格式
		this.setHeaderType(header.getHeaderType());
		//设置mti的消息格式
		this.setMtiType(header.getMtiType());
		//设置bitmap的消息格式
		this.setBitmapType(header.getBitmapType());
		return this;
	}
	
	/**
	 * <p>分别设置8583报文中各个字段域的消息数据格式</p>
	 * <p>当出现以下情况时，会忽略操作，直接返回，而不会抛出异常</p>
	 * <ol>
	 *   <li>字段索引小于2时；</li>
	 *   <li>使用64域规范时，字段索引大于64；</li>
	 *   <li>使用128域规范时，字段索引大于128；</li>
	 * </ol>
	 * @Title: set
	 * @Description: 分别设置8583报文中各个字段域的消息数据格式
	 * @param index 字段索引值
	 * @param type 字段消息类型约束
	 * @return Iso8583Factory
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午4:25:23
	 */
	public Iso8583MessageFactory set(int index,Iso8583FieldType type){
		if(index<2)
			return this;
		if(true == this.isBit128() && index >128)
			return this;
		if(false == this.isBit128() && index >64)
			return this;
		dataFactory.put(String.valueOf(index), type);
		type.setFieldIndex(String.valueOf(index));
		return this;
	}
	
	/**
	 * <p>获取指定索引的消息类型格式</p>
	 * <p>index为int类型是为了保证避免由Factory维护的类型暴露</p>
	 * @Title: getFieldType
	 * @Description: 获取指定索引的消息类型格式
	 * @param index 消息字段报文索引
	 * @return Iso8583FieldType
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午5:20:35
	 */
	public Iso8583FieldType getFieldType(int index){
		return getFieldType(String.valueOf(index));
	} 
	
	/**
	 * <p>获取指定索引的消息类型格式</p>
	 * @Title: getFieldType
	 * @Description: 获取指定索引的消息类型格式
	 * @param index 消息字段报文索引
	 * @return Iso8583FieldType
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午5:20:35
	 */
	private Iso8583FieldType getFieldType(String index){
		Iso8583FieldType fieldType = dataFactory.get(index);
		if(null == fieldType){
			throw new NullPointerException(String.format("没有找到当前索引的配置信息 ： %s", index));
		}
		return fieldType;
	} 
	
	/**
	 * <p>获取当前报文协议版本格式</p>
	 * @Title: isBit128
	 * @Description: 获取当前报文协议版本格式
	 * @return boolean 
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午5:24:54
	 */
	public boolean isBit128() {
		return bit128;
	}
	
	/**
	 * <p>将接受到的一个字符串格式的消息报文转换为一个Iso8583Message对象。</p>
	 * <p>ps:data 包含消息长度信息</p>
	 * @Title: parse
	 * @Description: 将接受到的一个字符串格式的消息报文转换为一个Iso8583Message对象
	 * @param data 包含报文长度的报文数据
	 * @return Iso8583Message
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午5:25:26
	 */
	public Iso8583Message parse(String data){
		//将接收到的String转换为byte[]
		byte[] srcData = EncodeUtil.bcd(data);
		return parse(srcData);
	}
	
	/**
	 * <p>将一个包括消息长度的byte[]格式的消息报文转换为一个Iso8583Message对象</p>
	 * @Title: parse
	 * @Description: 将一个byte[]格式的消息报文转换为一个Iso8583Message对象
	 * @param data 包含报文长度的报文数据
	 * @return Iso8583Message
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午5:28:25
	 */
	public Iso8583Message parse(byte[] data){
		return parseWithoutMsgLength(getDestData(data));
	}
	
	/**
	 * <p>将一个不包括消息长度的String格式的消息报文转换成为一个Iso8583Message对象</p>
	 * @Title: parseWithoutMsgLength
	 * @Description: 将一个不包括消息长度的String格式的消息报文转换成为一个Iso8583Message对象
	 * @param data 不包含消息长度的报文数据
	 * @return Iso8583Message
	 * @author Ajsgn@foxmail.com
	 * @date 2017年7月25日 下午3:57:28
	 */
	public Iso8583Message parseWithoutMsgLength(String data){
		return parseWithoutMsgLength(EncodeUtil.bcd(data));
	}
	
	/**
	 * <p>将一个不包括消息长度的byte[]格式的消息报文转换成为一个Iso8583Message对象</p>
	 * @Title: parseWithoutMsgLength
	 * @Description: 将一个不包括消息长度的byte[]格式的消息报文转换成为一个Iso8583Message对象
	 * @param data 不包含消息长度的报文数据
	 * @return Iso8583Message
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月29日 下午1:59:54
	 */
	public Iso8583Message parseWithoutMsgLength(byte[] data){
		//创建一个新的Iso8583Message对象
		System.out.println(EncodeUtil.hex(data));
		Iso8583Message message = new Iso8583Message(this);
		ByteArrayInputStream destIs = new ByteArrayInputStream(data);
		try {
			/*
			 * 将srcData转换为ByteArrayInputStream对象，由ByteArrayInputStream来管理数组的pos
			 * 用ByteArrayInputStream原因有2：
			 * 1、ByteArrayInputStream 自身有pos属性，方便管理数组游标方便读取
			 * 2、ByteArrayInputStream 内部持有buf，不需要做close()
			 */
			//顺序解析：tpdu-header-mti-bitmap-data
			//tpdu
			String tpdu = parse(destIs,getFieldType(Iso8583ConstantValue.TPDU.getValue()));
			message.setTpdu(tpdu);
			//header
			String header = parse(destIs,getFieldType(Iso8583ConstantValue.HEADER.getValue()));
			message.setHeader(header);
			//mti
			String mti = parse(destIs,getFieldType(Iso8583ConstantValue.MTI.getValue()));
			message.setMti(mti);
			//bitmap
			String strBitmap = parse(destIs,getFieldType(Iso8583ConstantValue.BITMAP.getValue()));
			//解析bitmap，由此决定之后去解析哪些字段信息  0110000000111100000000001000000100001010110100001000110000010001
			String strByteBitmap = EncodeUtil.binary(EncodeUtil.bcd(strBitmap));
			String index = "";
			//判断索引是否为“1”来觉得是否要解析当前域
			//不做strByteBitmap.chatAt(0)做判断，因为在创建Iso8583Message对象时，已经通过参数Iso8583Factory对象的 boolean bit128 知道报文格式规范
			for(int i=1;i<strByteBitmap.length();i++){
				//依次遍历下标，判断是否对当前索引的字段进行解析
				index = String.valueOf(strByteBitmap.charAt(i));
				if("1".equals(index)){
					//解析字段，得到String类型，方便后续程序处理
					String result = parseWithMti(destIs,getFieldType(String.valueOf(i+1)),mti);
					//将值设置到message中
					message.setValue(i+1, result);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
		
	}
	
	/**
	 * <p>解析字段内容信息</p>
	 * @Title: parse
	 * @Description: 解析字段内容信息
	 * @param is 数据来源
	 * @param fieldType 当前需要解析的字段配置信息，字段的类型，占用长度
	 * @return String 解析结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午5:41:52
	 */
	private String parse(InputStream is,Iso8583FieldType fieldType) throws IOException{
		return parseWithMti(is,fieldType,"");
	}
	
	/**
	 * <p>报文解析核心方法</p>
	 * @Title: parseWithMti
	 * @Description: 报文解析核心方法
	 * @param is 数据来源
	 * @param fieldType 当前正在解析的字段类型
	 * @param mti 当前的消息类型
	 * @return String 解析结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年7月25日 下午4:00:47
	 */
	private String parseWithMti(InputStream is,Iso8583FieldType fieldType,String mti) throws IOException{
		String result = "";
		SpecialFieldHandler fieldHandler = specialFieldHandlerMap.get(fieldType.getFieldIndex());
		//空值校验
		if(null != fieldHandler){
			Iso8583FieldType newType = new Iso8583FieldType(fieldHandler.forGetBytes(fieldType, mti),fieldType.getFieldLength());
			newType.setFieldIndex(fieldType.getFieldIndex());
			newType.setFillBlankStrategy(fieldType.getFillBlankStrategy());
//			//使用例外的数据类型
			fieldType = newType;
		}
		//根据字段类型，进行相应的解析动作
		switch (fieldType.getFieldTypeValue()) {
		case BINARY:{
			//定长处理：读取固定字节长度的报文数据
			byte[] content = fixedLengthRead(is, fieldType.getFieldLength());
			result = EncodeUtil.hex(content);
			break;
		}
		case CHAR:{
			//定长处理：读取固定字节长度的报文数据
			byte[] content = fixedLengthRead(is, fieldType.getFieldLength());
			result = new String(content,this.charset);
			break;
		}
		case NUMERIC:{
			//定长处理：读取固定字节长度的报文数据
			byte[] content = fixedLengthRead(is, fieldType.getFieldLength());
			result = EncodeUtil.hex(content);
			break;
		}
		case LLVAR:{
			//变长处理：读取指定字节的bcd编码数据做为数据长度，继续往后读
			byte[] content = variableLengthRead(is,1,false);
			result = new String(content,this.charset);
			break;
		}
		case LLLVAR:{
			//变长处理：读取指定字节的bcd编码数据做为数据长度，继续往后读
			byte[] content = variableLengthRead(is,2,false);
			result = new String(content,this.charset);
			break;
		}
		case LLLLVAR:{
			//变长处理：读取指定字节的bcd编码数据做为数据长度，继续往后读
			byte[] content = variableLengthRead(is,3,false);
			result = new String(content,this.charset);
			break;
		}
		case LLVAR_NUMERIC:{
			//变长处理：读取指定字节的bcd编码数据做为数据长度，继续往后读
			byte[] content = variableLengthRead(is,1,true);
			result = EncodeUtil.hex(content);
			break;
		}
		case LLLVAR_NUMERIC:{
			//变长处理：读取指定字节的bcd编码数据做为数据长度，继续往后读
			byte[] content = variableLengthRead(is,2,true);
			result = EncodeUtil.hex(content);
			break;
		}
		case LLLLVAR_NUMERIC:{
			//变长处理：读取指定字节的bcd编码数据做为数据长度，继续往后读
			byte[] content = variableLengthRead(is,3,true);
			result = EncodeUtil.hex(content);
			break;
		}
		default:
			break;
		}
		return result;
	}
	
	
	
	/**
	 * <p>解析原始数据，获得需要做解析的报文的byte[]数据</p>
	 * <p>读取factory.msgLength个长度的字节作为整体报文的长度</p>
	 * @Title: getDestData
	 * @Description: 解析原始数据，获得需要做解析的报文的byte[]数据
	 * @param srcData 初始数据信息
	 * @return byte[] 不再包含消息长度的byte[]
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午5:46:55
	 */
	private byte[] getDestData(byte[] srcData){
		//创建一个factory.msgLength个长度的byte[]用于去读取报文长度信息
		byte[] dataLength = new byte[this.msgLength];
		//目标信息数据
		byte[] destData = new byte[0];
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(srcData);
			//读取长度信息
			bais.read(dataLength);
			//转换为10进制数据
			int destLength = Integer.parseInt(EncodeUtil.hex(dataLength), 16);
			//创建等长的byte[]用于存放数据内容
			destData = new byte[destLength];
			bais.read(destData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destData;
	}
	
	/**
	 * <p>固定长度报文长度的解析</p>
	 * @Title: fixedLengthRead
	 * @Description: 固定长度报文长度的解析
	 * @param is 数据来源
	 * @param length 需要读取的字节长度
	 * @return byte[] 读取到的数据内容
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午5:59:49
	 */
	private byte[] fixedLengthRead(InputStream is,int length) throws IOException{
		byte[] content = new byte[length];
		is.read(content);
		return content;
	}
	
	/**
	 * <p>变长报文内容的解析</p>
	 * <p>1、读取固定长度的字节数做为整体消息的长度内容</p>
	 * <p>2、读取步骤1中的结果个的字节数做为实际内容</p>
	 * @Title: variableLengthRead
	 * @Description: 变长报文内容的解析
	 * @param is 数据来源
	 * @param dataLength 表示长度内容的字节数
	 * @param byteLength 是否表示字节长度 。true，则读取byteLength个字节内容;false，则读取byteLength/2个字节内容
	 * @return byte[] 读取到的有效消息内容
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午6:02:01
	 */
	private byte[] variableLengthRead(InputStream is,int dataLength,boolean byteLength) throws IOException{
		byte[] bLength = fixedLengthRead(is, dataLength);
		//判断是否表示读取字节长度 
		//byteLength == true 则读取byteLength个字节内容
		//byteLength != true 则读取byteLength/2个字节内容
		int length = byteLength?(Integer.parseInt(EncodeUtil.hex(bLength), 10)+1) / 1 : Integer.parseInt(EncodeUtil.hex(bLength), 10);
		return fixedLengthRead(is,length);
	}
	
	// >> getter & setter
	/**
	 * <p>获取表示报文长度信息的字节数</p>
	 * @Title: getMsgLength
	 * @Description: 获取表示报文长度信息的字节数
	 * @return int 表示当前报文协议表示报文长度的字节数
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午6:07:52
	 */
	public int getMsgLength(){
		return msgLength;
	}
	
	/**
	 * <p>设置TPDU的消息类型</p>
	 * @Title: setTpduType
	 * @Description: 设置TPDU的消息类型
	 * @param type TPDU的消息类型
	 * @return Iso8583Factory 返回自身，便于做链式调用
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午6:08:32
	 */
	private Iso8583MessageFactory setTpduType(Iso8583FieldType type) {
		dataFactory.put(Iso8583ConstantValue.TPDU.getValue(), type);
		return this;
	}
	
	/**
	 * <p>获取TPDU的消息类型</p>
	 * @Title: getTpduType
	 * @Description: 获取TPDU的消息类型
	 * @return Iso8583FieldType TPDU的消息类型
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午6:09:30
	 */
	public Iso8583FieldType getTpduType(){
		return dataFactory.get(Iso8583ConstantValue.TPDU.getValue());
	}
	
	/**
	 * <p>设置HEADER的消息类型格式</p>
	 * @Title: setHeaderType
	 * @Description: 设置HEADER的消息类型格式
	 * @param type HEADER的消息类型格式
	 * @return Iso8583Factory 返回自身对象，方便做链式调用
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午6:10:02
	 */
	private Iso8583MessageFactory setHeaderType(Iso8583FieldType type) {
		dataFactory.put(Iso8583ConstantValue.HEADER.getValue(), type);
		return this;
	}
	
	/**
	 * <p>获取HEADER的消息类型格式</p>
	 * @Title: getHeaderType
	 * @Description: 获取HEADER的消息类型格式
	 * @return Iso8583FieldType
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午6:10:42
	 */
	public Iso8583FieldType getHeaderType(){
		return dataFactory.get(Iso8583ConstantValue.HEADER.getValue());
	}
	
	/**
	 * <p>设置MTI的消息类型格式</p>
	 * @Title: setMtiType
	 * @Description: 设置MTI的消息类型格式
	 * @param type MTI的消息类型格式
	 * @return Iso8583Factory 返回自身对象，方便做链式调用
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午6:11:10
	 */
	private Iso8583MessageFactory setMtiType(Iso8583FieldType type) {
		dataFactory.put(Iso8583ConstantValue.MTI.getValue(), type);
		return this;
	}
	
	/**
	 * <p>获取MTI的消息类型格式</p>
	 * @Title: getMtiType
	 * @Description: 获取MTI的消息类型格式
	 * @return Iso8583FieldType MTI的消息类型格式
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午6:11:45
	 */
	public Iso8583FieldType getMtiType(){
		return dataFactory.get(Iso8583ConstantValue.MTI.getValue());
	}
	
	/**
	 * <p>设置BITMAP的消息类型格式</p>
	 * @Title: setBitmapType
	 * @Description: 设置BITMAP的消息类型格式
	 * @param type BITMAP的消息类型格式
	 * @return Iso8583Factory
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午6:12:09
	 */
	private Iso8583MessageFactory setBitmapType(Iso8583FieldType type) {
		dataFactory.put(Iso8583ConstantValue.BITMAP.getValue(), type);
		return this;
	}
	
	/**
	 * <p>获取BITMAP的消息类型格式</p>
	 * @Title: getBitmapType
	 * @Description: 获取BITMAP的消息类型格式
	 * @return Iso8583FieldType 返回自身对象，方便做链式调用
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午6:12:44
	 */
	public Iso8583FieldType getBitmapType(){
		return dataFactory.get(Iso8583ConstantValue.BITMAP.getValue());
	}
	
	/**
	 * <p>获取当前工厂的字符集</p>
	 * @Title: getCharset
	 * @Description: 获取当前工厂的字符集
	 * @return Charset 当前工厂的字符集
	 * @author Ajsgn@foxmail.com
	 * @date 2017年6月29日 下午4:02:16
	 */
	public Charset getCharset() {
		return charset;
	}
	
	/**
	 * <p>设置需要特殊处理的字段的处理器</p>
	 * @Title: setSpecialFieldHandle
	 * @Description: 设置需要特殊处理的字段的处理器
	 * @param index 处理字段的索引值
	 * @param specialFieldHandle 特殊字段的处理器
	 * @return Iso8583Factory 自身对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年6月29日 下午4:05:56
	 */
	public Iso8583MessageFactory setSpecialFieldHandle(int index,SpecialFieldHandler specialFieldHandle){
		if(index>1 && index<129){
			specialFieldHandlerMap.put(String.valueOf(index), specialFieldHandle);
		}
		return this;
	}
	
	/**
	 * <p>获取指定索引下需要特殊处理的字段的处理器</p>
	 * @Title: getSpecialFieldHandle
	 * @Description: 获取指定索引下需要特殊处理的字段的处理器
	 * @param index 字段的索引值
	 * @return SpecialFieldHandle 字段所对应的处理器
	 * @author Ajsgn@foxmail.com
	 * @date 2017年6月29日 下午4:07:38
	 */
	public SpecialFieldHandler getSpecialFieldHandler(String index){
		SpecialFieldHandler handle = specialFieldHandlerMap.get(String.valueOf(index));
		return handle;
	}
	
}
