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

/**
 * <p>解析器类型枚举</p>
 * @ClassName: TlvParserType
 * @Description: 解析器类型枚举
 * @author Ajsgn@foxmail.com
 * @date 2017年8月20日 上午12:08:08
 */
public enum TlvParserType {
	
	/**
	 * 右4bit为1的55域解析器对象
	 */
	ISO8583_FIELD_55_VERSION4,
	/**
	 * 右5bit为1的55域解析器对象
	 */
	ISO8583_FIELD_55_VERSION5,
	;
	
}
