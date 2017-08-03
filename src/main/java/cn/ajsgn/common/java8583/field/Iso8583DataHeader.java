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
package cn.ajsgn.common.java8583.field;

/**
 * <p>消息头报文类型声明类</p>
 * @ClassName: Iso8583DataHeader
 * @Description: 消息头报文类型声明类
 * @author Ajsgn@foxmail.com
 * @date 2017年3月23日 下午12:42:18
 */
public class Iso8583DataHeader {
	
	/**
	 * <p>tpdu 消息头类型</p>
	 */
	private Iso8583FieldType tpduType;
	/**
	 * <p>header tpdu后的字段的消息类型</p>
	 */
	private Iso8583FieldType headerType;
	/**
	 * <p>mti 的消息类型</p>
	 */
	private Iso8583FieldType mtiType;
	/**
	 * <p>bitmap 的消息类型</p>
	 */
	private Iso8583FieldType bitmapType;
	
	/**
	 * <p>构造函数</p> 
	 * @param tpduType tpduType
	 * @param headerType headerType
	 * @param mtiType mitType
	 * @param bitmapType bitmapType
	 */
	public Iso8583DataHeader(Iso8583FieldType tpduType, Iso8583FieldType headerType, Iso8583FieldType mtiType, Iso8583FieldType bitmapType) {
		this.tpduType = tpduType;
		this.headerType = headerType;
		this.mtiType = mtiType;
		this.bitmapType = bitmapType;
	}
	
	public Iso8583FieldType getTpduType() {
		return tpduType;
	}
	
	public Iso8583FieldType getHeaderType() {
		return headerType;
	}

	public Iso8583FieldType getMtiType() {
		return mtiType;
	}

	public Iso8583FieldType getBitmapType() {
		return bitmapType;
	}
	
}
