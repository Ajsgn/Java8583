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
package cn.ajsgn.common.java8583.ext.tlv.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import cn.ajsgn.common.java8583.ext.tlv.TlvObject;
import cn.ajsgn.common.java8583.ext.tlv.TlvParser;
import cn.ajsgn.common.java8583.ext.tlv.TlvValue;
import cn.ajsgn.common.java8583.util.EncodeUtil;
import cn.ajsgn.common.java8583.util.IoUtil;

/**
 * <p>ISO8583报文55域字段解析器实现</p>
 * <p>因看文档发现对此域有两种描述，主要在于长度位的描述，即tag阶段，如果右4/5bit为1，则tag占两个字节</p>
 * <p>所以该解析器通过描述分为version4与version5两个解析器静态工厂方法用于构建对象</p>
 * @ClassName: Iso8583Filed55Parser
 * @Description: ISO8583报文55域字段解析器
 * @author Ajsgn@foxmail.com
 * @date 2017年8月19日 下午11:57:37
 */
class Iso8583Filed55Parser implements TlvParser{
	
	/**
	 * 解析版本类型
	 */
	private byte version = 0b00001111;
	
	/**
	 * 构造函数
	 * @param version 解析版本
	 */
	private Iso8583Filed55Parser(byte version){
		this.version = version;
	}
	
	/**
	 * <p>静态工厂方法，创建右4bit为1的解析器</p>
	 * @Title: version4
	 * @Description: 创建右4bit为1的解析器
	 * @return Iso8583Filed55Parser 解析器对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月20日 上午12:00:25
	 */
	public static Iso8583Filed55Parser version4(){
		return new Iso8583Filed55Parser((byte)0b00001111);
	}
	
	/**
	 * <p>静态工厂方法，创建右5bit为1的解析器</p>
	 * @Title: version5
	 * @Description: 创建右5bit为1的解析器
	 * @return Iso8583Filed55Parser 解析器对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月20日 上午12:01:27
	 */
	public static Iso8583Filed55Parser version5(){
		return new Iso8583Filed55Parser((byte)0b00011111);
	}
	
	@Override
	public TlvObject tlvParse(String data) {
		//输入流转换
		ByteArrayInputStream bais = new ByteArrayInputStream(EncodeUtil.bcd(data));
		//解析结果
		LinkedHashMap<String, TlvValue> values = new LinkedHashMap<String, TlvValue>();
		//构建结果对象
		TlvObject tlvObject = new Field55TlvObject(values);
		try{
			do{
				//依次解析
				Field55TlvValue tlvValue = read(bais);
				//放置结果
				values.put(tlvValue.getTagName(), tlvValue);
			}while(bais.available() > 0);
		}catch(IOException e){
			e.printStackTrace();
		}
		return tlvObject;
	}
	
	/**
	 * <p>数据依次读取解析</p>
	 * @Title: read
	 * @Description: 数据依次读取解析
	 * @param bais 输入流对象，解析的数据来源
	 * @return Field55TlvValue 解析的结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月20日 上午12:03:47
	 */
	private Field55TlvValue read(ByteArrayInputStream bais) throws IOException {
		//解析tagName阶段
		byte[] resultBytes = IoUtil.read(bais,1);
		if(version == (version & (resultBytes[0]))){
			byte[] tempByte = IoUtil.read(bais,1);
			resultBytes = new byte[]{resultBytes[0],tempByte[0]};
		}
		String tagName = EncodeUtil.hex(resultBytes);
		//解析tagLength阶段
		int tagLength = readLength(bais);
		//解析tagValue阶段
		String tagValue = readValue(bais,tagLength);
		//构建对象
		Field55TlvValue result = new Field55TlvValue(tagName,tagLength,tagValue);
		return result;
	}
	
	/**
	 * <p>tagLength阶段解析</p>
	 * @Title: readLength
	 * @Description: tagLength阶段解析
	 * @param bais 解析的数据来源
	 * @return int tagLength
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月20日 上午12:04:42
	 */
	private int readLength(ByteArrayInputStream bais) throws IOException {
		byte[] lengthByte = IoUtil.read(bais,1);
		byte dataLength = lengthByte[0];
		int result = 0;
		if(dataLength < 0x00){
//			//获取需要读取的长度
//			byte[] tagLengthByte = IoUtil.read(bais,dataLength & 0x7F);
//			// FIXME 不太明白多字节长度的长度计算规则。。。
//			result = 0xFF & tagLengthByte[tagLengthByte.length-1];
			throw new RuntimeException("暂无实现相关方法（Ajsgn@foxmail.com）");
		}else{
			result = dataLength;
		}
		return result;
	}
	
	/**
	 * <p>tagValue阶段解析</p>
	 * @Title: readValue
	 * @Description: tagVlaue阶段解析
	 * @param bais 数据来源
	 * @param tagLength 需要读取的数据长度
	 * @return String 读取数据结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月20日 上午12:05:32
	 */
	private String readValue(ByteArrayInputStream bais,int tagLength) throws IOException{
		return EncodeUtil.hex(IoUtil.read(bais, tagLength));
	}
	
}
