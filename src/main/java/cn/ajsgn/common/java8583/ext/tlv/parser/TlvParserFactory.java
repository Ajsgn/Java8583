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

import cn.ajsgn.common.java8583.ext.tlv.TlvParser;

/**
 * <p>Tlv字段解析器工厂</p>
 * @ClassName: TlvParserFactory
 * @Description: Tlv字段解析器工厂
 * @author Ajsgn@foxmail.com
 * @date 2017年8月20日 上午12:06:30
 */
public class TlvParserFactory {
	
	/**
	 * <p>获取字段解析器</p>
	 * @Title: forTlvParse
	 * @Description: 获取一个字段解析器
	 * @param type 字段解析器类型
	 * @return TlvParser 解析器对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月20日 上午12:07:03
	 */
	public static TlvParser forTlvParse(TlvParserType type){
		Iso8583Filed55Parser parser = null;
		if(TlvParserType.ISO8583_FIELD_55_VERSION4 == type){
			parser = Iso8583Filed55Parser.version4();
		}else if(TlvParserType.ISO8583_FIELD_55_VERSION5 == type){
			parser = Iso8583Filed55Parser.version5();
		}
		return parser;
	}
	
}
