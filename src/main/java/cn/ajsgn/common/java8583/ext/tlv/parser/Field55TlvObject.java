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

import java.util.LinkedHashMap;
import java.util.LinkedList;

import cn.ajsgn.common.java8583.ext.tlv.TlvObject;
import cn.ajsgn.common.java8583.ext.tlv.TlvValue;

/**
 * <p>Iso8583报文55域对象抽象</p>
 * @ClassName: Field55TlvObject
 * @Description: Iso8583报文55域对象抽象
 * @author Ajsgn@foxmail.com
 * @date 2017年8月19日 下午11:53:11
 */
class Field55TlvObject implements TlvObject{
	
	/**
	 * 对象内容体
	 */
	LinkedHashMap<String, TlvValue> me = new LinkedHashMap<String, TlvValue>();
	
	/**
	 * 构造函数
	 */
	Field55TlvObject(LinkedHashMap<String, TlvValue> values){
		this.me = values;
	}

	@Override
	public String toLocalString() {
		StringBuilder sb = new StringBuilder();
		for(TlvValue value:values()){
			sb.append(value.toLocalString());
		}
		return sb.toString();
	}

	@Override
	public boolean contains(String tagName) {
		return me.containsKey(tagName);
	}

	@Override
	public TlvValue get(String tagName) {
		return me.get(tagName);
	}

	@Override
	public LinkedList<TlvValue> values() {
		return new LinkedList<TlvValue>(me.values());
	}

	@Override
	public TlvObject put(TlvValue tlvValue) {
		if(null != tlvValue && tlvValue instanceof Field55TlvValue){
			me.put(tlvValue.getTagName(), tlvValue);
		}
		return this;
	}

	@Override
	public TlvObject remove(TlvValue tlvValue) {
		if(null != tlvValue && tlvValue instanceof Field55TlvValue){
			me.remove(tlvValue.getTagName());
		}
		return this;
	}

	@Override
	public String toString() {
		return "Field55TlvObject [me=" + me + "]";
	}
	
}
