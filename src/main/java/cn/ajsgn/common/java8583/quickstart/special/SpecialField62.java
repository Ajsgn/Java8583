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
package cn.ajsgn.common.java8583.quickstart.special;

import cn.ajsgn.common.java8583.field.Iso8583FieldType;
import cn.ajsgn.common.java8583.field.Iso8583FieldType.FieldTypeValue;
import cn.ajsgn.common.java8583.special.SpecialField;

/**
 * <p>一个简单的62域特殊处理器</p>
 * <p>用于解决签到62域BCD编码，其他请求，62域返回ASCII编码</p>
 * @ClassName: SpecialField62
 * @Description: 一个简单的62域特殊处理器
 * @author Ajsgn@foxmail.com
 * @date 2017年7月25日 下午4:55:10
 */
public class SpecialField62 implements SpecialField{
	
	@Override
	public FieldTypeValue forParse(Iso8583FieldType fieldType, String mti) {
		if("0810".equals(mti)){
			return Iso8583FieldType.FieldTypeValue.LLLVAR_BYTE_NUMERIC;
		}else{
			return fieldType.getFieldTypeValue();
		}
	}

	@Override
	public FieldTypeValue forGetBytes(Iso8583FieldType fieldType, String mti) {
		if("0810".equals(mti)){
			return Iso8583FieldType.FieldTypeValue.LLLVAR_BYTE_NUMERIC;
		}else{
			return fieldType.getFieldTypeValue();
		}
	}

}
