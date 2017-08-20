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
 * <p>TLV对象中每一段数据的抽象</p>
 * <p>不可变类</p>
 * @ClassName: TlvValue
 * @Description: TLV对象中每一段数据的抽象
 * @author Ajsgn@foxmail.com
 * @date 2017年8月19日 下午11:49:36
 */
public interface TlvValue {
	
	/**
	 * <p>获取字段名称</p>
	 * @Title: getTagName
	 * @Description: 获取字段名称
	 * @return String 字段名称
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月19日 下午11:50:10
	 */
	public String getTagName();
	
	/**
	 * <p>获取字段长度</p>
	 * @Title: getTagLength
	 * @Description: 获取字段长度
	 * @return int 字段长度
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月19日 下午11:50:56
	 */
	public int getTagLength();
	
	/**
	 * <p>获取字段值</p>
	 * @Title: getTagValue
	 * @Description: 获取字段值
	 * @return String 字段值
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月19日 下午11:51:25
	 */
	public String getTagValue();
	
	/**
	 * <p>子字段的本地化展示结果，即转换为报文后的结果</p>
	 * @Title: toLocalString
	 * @Description: 子字段的报文转换结果
	 * @return String 转换结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月19日 下午11:51:51
	 */
	public String toLocalString();
	
}
