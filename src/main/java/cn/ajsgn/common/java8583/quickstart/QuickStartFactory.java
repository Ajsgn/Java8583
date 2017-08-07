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
package cn.ajsgn.common.java8583.quickstart;

import java.nio.charset.Charset;

import cn.ajsgn.common.java8583.core.Iso8583MessageFactory;
import cn.ajsgn.common.java8583.field.Iso8583FillBlankStrategy;
import cn.ajsgn.common.java8583.field.Iso8583DataHeader;
import cn.ajsgn.common.java8583.field.Iso8583FieldType;

/**
 * 封装消息格式的工厂类</br>
 * 提供静态工厂方法，但每次都会new对象，所以，建议开发对生成的对象做缓存操作
 * @ClassName: BankFactory
 * @Description: 用于封装消息格式的工厂类
 * @author Ajsgn@foxmail.com
 * @date 2017年3月24日 上午10:52:38
 */
class QuickStartFactory {
	
	/**
	 * 供熟悉框架使用的factory
	 * @Title: forQuickStart
	 * @Description: 供熟悉框架使用的factory
	 * @return Iso8583MessageFactory
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月3日 下午4:30:29
	 */
	public static Iso8583MessageFactory forQuickStart(){
		Iso8583DataHeader dataHeaderType = new Iso8583DataHeader(
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,5),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,6),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8));
		Iso8583MessageFactory factory = new Iso8583MessageFactory(2,false,Charset.forName("GBK"),dataHeaderType);
		factory.set( 2, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(Iso8583FillBlankStrategy.rightAppendStrategy('F', false)))
			   .set( 3, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
			   .set( 4, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,6))
			   .set(11, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
			   .set(12, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
			   .set(13, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
			   .set(14, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
			   .set(15, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
			   .set(22, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2).setFillBlankStrategy(Iso8583FillBlankStrategy.rightAppendStrategy('0', false)))
			   .set(23, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
			   .set(25, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,1))
			   .set(26, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,1))
			   .set(32, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(Iso8583FillBlankStrategy.rightAppendStrategy('F', false)))
			   .set(35, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(Iso8583FillBlankStrategy.rightAppendStrategy('F', false)))
			   .set(36, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0).setFillBlankStrategy(Iso8583FillBlankStrategy.rightAppendStrategy('F', false)))
			   .set(37, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,12))
			   .set(38, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,6))
			   .set(39, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,2))
			   .set(41, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,8))
			   .set(42, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,15))
			   .set(43, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,40))
			   .set(44, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR,0))
			   .set(48, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0))
			   .set(49, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,3))
			   .set(52, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8))
			   .set(53, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8))
			   .set(54, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
			   .set(55, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
			   .set(58, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
			   .set(60, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0))
			   .set(61, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0).setFillBlankStrategy(Iso8583FillBlankStrategy.rightAppendStrategy('F', false)))
			   .set(62, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
			   .set(63, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
			   .set(64, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8));
		return factory;
	}
	
}
