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
package cn.ajsgn.common.java8583.field;

/**
 * <p>8583报文字段抽象，不可变类<p>
 * @ClassName: Iso8583Field
 * @Description: 每一个8583报文字段抽象
 * @author Ajsgn@foxmail.com
 * @date 2017年3月23日 下午12:43:27
 */
public final class Iso8583Field implements Comparable<Iso8583Field>{
	
	/**
	 * 报文字段索引
	 */
	private int index = 0;
	/**
	 * 报文值
	 */
	private String value = "";
	/**
	 * 报文格式类型
	 */
	private Iso8583FieldType fieldType = null;
	
	/**
	 * <p>构造函数</p> 
	 * @param index 当前字段索引
	 * @param value 当前字段值
	 * @param fieldType 当前字段类型
	 */
	public Iso8583Field(int index, String value, Iso8583FieldType fieldType) {
		this.index = index;
		this.value = value;
		this.fieldType = fieldType;
	}
	
	/**
	 * <p>获取当前字段索引</p>
	 * @Title: getIndex
	 * @Description: 获取当前字段索引
	 * @return int 当前字段索引
	 * @author g.yang@i-vpoints.com
	 * @date 2017年7月25日 上午11:45:09
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * <p>获取当前字段值</p>
	 * @Title: getValue
	 * @Description: 获取当前字段值
	 * @return String 当前字段值
	 * @author g.yang@i-vpoints.com
	 * @date 2017年7月25日 上午11:45:49
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * <p>获取当前字段类型</p>
	 * @Title: getFieldType
	 * @Description: 获取当前字段类型
	 * @return Iso8583FieldType 当前字段类型
	 * @author g.yang@i-vpoints.com
	 * @date 2017年7月25日 上午11:46:17
	 */
	public Iso8583FieldType getFieldType() {
		return fieldType;
	}

	@Override
	public int compareTo(Iso8583Field field) {
		return this.index - field.index;
	}
	
	@Override
	public String toString() {
		return "Iso8583IsoField [index=" + index + ", value=" + value + ", fieldType=" + fieldType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldType == null) ? 0 : fieldType.hashCode());
		result = prime * result + index;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Iso8583Field other = (Iso8583Field) obj;
		if (fieldType == null) {
			if (other.fieldType != null)
				return false;
		} else if (!fieldType.equals(other.fieldType))
			return false;
		if (index != other.index)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
