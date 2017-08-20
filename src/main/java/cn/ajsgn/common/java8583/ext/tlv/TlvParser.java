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
package cn.ajsgn.common.java8583.ext.tlv;

/**
 * <p>TLV字段解析器</p>
 * @ClassName: TlvParser
 * @Description: TLV字段解析器
 * @author Ajsgn@foxmail.com
 * @date 2017年8月19日 下午11:47:46
 */
public interface TlvParser {
	
	/**
	 * <p>将一个字符串数据内容解析成一个TlvObject对象</p>
	 * @Title: tlvParse
	 * @Description: 将一个字符串数据内容解析成一个TlvObject对象
	 * @param data 需要被解析的数据
	 * @return TlvObject 解析结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月19日 下午11:48:13
	 */
	public TlvObject tlvParse(String data);
	
}
