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
package cn.ajsgn.common.java8583.constant;

/**
 * 消息头常量表示
 * @ClassName: Iso8583ConstantValue
 * @Description: 消息头常量表示
 * @author Ajsgn@foxmail.com
 * @date 2017年3月23日 下午12:45:59
 */
public enum Iso8583ConstantValue {
	
	/**
	 * TPDU
	 */
	TPDU("TPDU"),
	/**
	 * MTI
	 */
	MTI("MTI"),
	/**
	 * BITMAP
	 */
	BITMAP("BITMAP"),
	/**
	 * HEADER
	 */
	HEADER("HEADER");
	
	private String value = "";
	
	private Iso8583ConstantValue(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
	
}
