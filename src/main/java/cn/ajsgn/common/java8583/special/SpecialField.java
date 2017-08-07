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
package cn.ajsgn.common.java8583.special;

import cn.ajsgn.common.java8583.field.Iso8583FieldType;

/**
 * <p>特殊字段处理器</p>
 * <p>如果同一个字段，在不同的消息中有不同的数据格式，则无法保证Iso8583Factory使用单例模式，同时也意味着，开发者要维护多个factory对象，并要建立调用映射关系。</p>
 * <p>为了保证可以使Iso8583Factory保证单例运行，避免开发建立抵用映射，故添加该接口</p>
 * <p>当然，如果是做全接口开发，可能会出现涵盖不了的情况，如果是这样，还是请创建多个facotry实例，确保安全</p>
 * @ClassName: SpecialFieldHandler
 * @Description: 用于对字段提供特殊处理的接口抽象
 * @author Ajsgn@foxmail.com
 * @date 2017年6月29日 下午3:45:26
 */
public interface SpecialField {
	
	/**
	 * <p>做parse()解析报文时的特殊处理</p>
	 * @Title: forParse
	 * @Description: 解析报文时的特殊处理
	 * @param data 当前字段的数据内容
	 * @param fieldType 当前字段配置的字段类型
	 * @param mti 当前消息类型
	 * @param factory 解析工厂
	 * @return SpecialFieldHandleResult 处理结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年6月29日 下午3:48:27
	 */
	public abstract Iso8583FieldType.FieldTypeValue forParse(Iso8583FieldType fieldType,String mti);
	
	/**
	 * <p>当做getBytes()时的特殊处理</p>
	 * @Title: forGetBytes
	 * @Description: 当做getBytes()时的特殊处理
	 * @param data 当前字段的数据内容
	 * @param fieldType 当前字段配置的字段类型
	 * @param mti 当前消息类型
	 * @param factory 解析工厂
	 * @return SpecialFieldHandleResult 处理结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年6月29日 下午3:50:53
	 */
	public abstract Iso8583FieldType.FieldTypeValue forGetBytes(Iso8583FieldType fieldType,String mti);
	
}
