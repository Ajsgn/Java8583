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

import java.util.LinkedList;

/**
 * <p>LV字段对象抽象，定义基本方法</p>
 * @ClassName: TlvObject
 * @Description: TLV字段对象抽象，定义基本方法
 * @author Ajsgn@foxmail.com
 * @date 2017年8月19日 下午11:36:25
 */
public interface TlvObject {
	
	/**
	 * <p>本地化字符串操作，转换为符合文档的字符串表现形式</p>
	 * <p>由字段文档描述规定</p>
	 * @Title: toLocalString
	 * @Description: 本地化字符串操作，转换为符合文档的字符串表现形式
	 * @return String 返回转换后的结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月19日 下午11:36:59
	 */
	public abstract String toLocalString();
	
	/**
	 * <p>判断当前TLV对象中是否包含了当前字段内容</p>
	 * @Title: contains
	 * @Description: 判断当前TLV对象中是否包含了当前字段内容
	 * @param tlvKey 字段名称
	 * @return boolean 包含结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月19日 下午11:38:22
	 */
	public abstract boolean contains(String tagName);
	
	/**
	 * <p>获取某一字段值，如果没有，返回null</p>
	 * @Title: get
	 * @Description: 返回某一字段值，如果没有返回null
	 * @param tlvKey 字段名称
	 * @return TlvValue 期望对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月19日 下午11:39:29
	 */
	public abstract TlvValue get(String tagName);
	
	/**
	 * <p>获取所有的字段描述</p>
	 * <p>返回集合对象实际为内容的拷贝，不能通过修改返回结果来修改当前对象</p>
	 * @Title: values
	 * @Description: 获取所有的字段描述
	 * @return LinkedList<TlvValue> 当前tlv对象中，所有的字段描述
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月19日 下午11:41:46
	 */
	public abstract LinkedList<TlvValue> values();
	
	/**
	 * <p>添加或者修改一个字段内容值</p>
	 * <p>相同对象应当只接受自己所能处理的对象内容。</p>
	 * @Title: put
	 * @Description: 添加或者修改一个字段内容值
	 * @param tlvValue 需要添加或修改的字段值
	 * @return TlvObject 返回当前对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月19日 下午11:44:26
	 */
	public abstract TlvObject put(TlvValue tlvValue);
	
	/**
	 * <p>移除一个对象值</p>
	 * <p>同put方法一样，该方法只会处理属于自己控制范围内的字段内容</p>
	 * @Title: remove
	 * @Description: 移除一个对象值
	 * @param tlvValue 需要移除的字段值
	 * @return TlvObject 返回自身对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月19日 下午11:46:04
	 */
	public abstract TlvObject remove(TlvValue tlvValue);

}
