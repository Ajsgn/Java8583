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

import cn.ajsgn.common.java8583.ext.tlv.TlvValue;

/**
 * 55域字段抽象
 * @ClassName: Field55TlvValue
 * @Description: 55域字段抽象
 * @author Ajsgn@foxmail.com
 * @date 2017年8月20日 上午10:42:27
 */
class Field55TlvValue implements TlvValue{
	
	/**
	 * tag
	 */
	private String tagName;
	/**
	 * length
	 */
	private int tagLength;
	/**
	 * value
	 */
	private String tagValue;
	
	Field55TlvValue(String tagName,int tagLength,String tagValue){
		this.tagName = tagName;
		this.tagLength = tagLength;
		this.tagValue = tagValue;
	}
	
	Field55TlvValue(String tagName,String tagValue){
		int tagLength = tagValue.length();
		this.tagName = tagName;
		this.tagLength = (tagLength + tagLength % 2) / 2;
		this.tagValue = tagValue;
	}

	@Override
	public String getTagName() {
		return tagName;
	}

	@Override
	public int getTagLength() {
		return tagLength;
	}

	@Override
	public String getTagValue() {
		return tagValue;
	}

	@Override
	public String toLocalString() {
		StringBuilder result = new StringBuilder();
		result.append(getTagName());
		int tagLength = getTagLength() <0 ? getTagValue().length() / 2 : getTagLength();
		if(tagLength > 0x7F){
			// FIXME 不太明白55域多字节长度计算规则
			throw new RuntimeException("暂无实现相关方法（Ajsgn@foxmail.com）");
		}else if(tagLength > 0x0F){
			result.append(Integer.toHexString(getTagLength()).toUpperCase());
		}else{
			result.append("0");
			result.append(Integer.toHexString(getTagLength()).toUpperCase());
		}
		result.append(getTagValue());
		return result.toString();
	}

	@Override
	public String toString() {
		return "Field55TlvValue [tagName=" + tagName + ", tagLength=" + tagLength + ", tagValue=" + tagValue + "]";
	}
	
}
