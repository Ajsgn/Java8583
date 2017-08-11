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
 * <p>字段类型抽象</p>
 * <p>如果字段类型为 NUMERIC，LLVAR_NUMERIC，LLLVAR_NUMERIC，LLLLVAR_NUMERIC类型，因为使用的是BCD编码。所以，当数据长度为奇数时，会触发使用补位策略进行填充。</p>
 * <p>默认使用策略：左对齐，右补‘0’，长度计算不包含填充位。</p>
 * <p>如果希望改变填充策略，则可以通过调用 setFillBlankStrategy({@link Iso8583FillBlankStrategy} fillBlankStrategy) 来完成策略的修改</p>
 * @ClassName: Iso8583FieldType
 * @Description: 字段类型抽象
 * @author Ajsgn@foxmail.com
 * @date 2017年3月23日 下午12:44:53
 */
public class Iso8583FieldType {
	
	/**
	 * <p>字段类型描述</p>
	 */
	private FieldTypeValue fieldTypeValue;
	/**
	 * <p>字段位索引</p>
	 */
	private String fieldIndex = "";
	/**
	 * <p>字段所占长度</p>
	 * <p>对于变长字段，该字段没有特殊意义</p>
	 */
	private int fieldLength;
	/**
	 * <p>字段填充策略</p>
	 * <p>默认使用策略：左对齐，右补‘0’，长度计算不包含填充位。</p>
	 */
	private Iso8583FillBlankStrategy fillBlankStrategy = null;
	
	/**
	 * <p>构造函数</p>
	 * @param fieldTypeName 字段类型名称
	 * @param fieldLength 字段所占用字节长度
	 */
	public Iso8583FieldType(FieldTypeValue fieldTypeValue, int fieldLength) {
		this.fieldTypeValue = fieldTypeValue;
		this.fieldLength = fieldLength;
	}
	
	/**
	 * <p>获取字段类型</p>
	 * @Title: getFieldTypeValue
	 * @Description: 获取字段类型
	 * @return Iso8583FieldTypeName
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午10:49:05
	 */
	public FieldTypeValue getFieldTypeValue(){
		return fieldTypeValue;
	}
	
	/**
	 * <p>获取字段类型所占用字节长度</p>
	 * @Title: getFieldLength
	 * @Description: 获取字段类型所占用字节长度
	 * @return int 所占用的字节长度
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午10:49:21
	 */
	public int getFieldLength() {
		return fieldLength;
	}

	@Override
	public String toString() {
		return "IsoFieldType [fieldTypeValue=" + fieldTypeValue + ", fieldLength=" + fieldLength + "]";
	}

	/**
	 * <p>获取字段类型所占用字节长度</p>
	 * <p>ps，通常该功能只使用与字段类型为:LLVAR_NUMERIC,LLLVAR_NUMERIC，没有做非类型判断</p>
	 * @Title: getFieldLength
	 * @Description: 获取字段类型所占用字节长度
	 * @return int 所占用的字节长度
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午10:49:21
	 */
	public Iso8583FillBlankStrategy getFillBlankStrategy() {
		//当需要获取策略信息时，检查是否有做设置，如果没有相关策略，则使用一个默认策略
		fillBlankStrategyCheck();
		return fillBlankStrategy;
	}
	
	/**
	 * 设置补位策略，返回当前对象本身，方便链式调用</br>
	 * 参数如果为null，并没有拥有自己的补位策略，则会使用一个默认补位策略；如果已经有补位策略，则放弃操作
	 * @Title: setFillBlankStrategy
	 * @Description: 设置补位策略
	 * @param fillBlankStrategy 补位策略
	 * @return Iso8583FieldType
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月27日 上午10:02:57
	 */
	public Iso8583FieldType setFillBlankStrategy(Iso8583FillBlankStrategy fillBlankStrategy) {
		// 如果设置的策略为null则判断当前是否已经有设置填补策略，没有的话则使用一个默认策略，如果有，则放弃操作
		if(null == fillBlankStrategy)
			fillBlankStrategyCheck();
		else
			this.fillBlankStrategy = fillBlankStrategy;	//策略不为空，则使用一个新的填补策略
		return this;
	}
	
	/**
	 * 检查是否有补位策略，如果没有，则使用一个默认补位策略
	 * @Title: fillBlankStrategyCheck
	 * @Description: 检查是否有补位策略，如果没有，则使用一个默认补位策略
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月27日 上午10:02:50
	 */
	private void fillBlankStrategyCheck(){
		if(null == this.fillBlankStrategy)
			this.fillBlankStrategy = Iso8583FillBlankStrategy.rightAppendStrategy('0', false);
	}

	public String getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(String fieldIndex) {
		this.fieldIndex = fieldIndex;
	}
	
	/**
	 * <p>消息字段类型名称</p>
	 * <p>用于约束字段名称的类型</p>
	 * @ClassName: Iso8583FieldTypeValue
	 * @Description: 消息字段类型格式
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月23日 下午12:46:16
	 */
	public static enum FieldTypeValue {
		/**
		 * <p>使用字符长度来描述BCD编码长度</p>
		 * <p>使用BCD编码方式进行编码处理</p>
		 */
		NUMERIC,
		/**
		 * <p>字节长度来描述BCD编码</p>
		 * <p>使用字节长度来描述BCD编码方式进行编码处理</p>
		 * <p>暂未实现，用NUMERIC方式替代</p>
		 */
		BYTE_NUMERIC,
		/**
		 * <p>字符类型字段</p>
		 * 使用ASCII编码方式编码
		 */
		CHAR,
		/**
		 * <p>1个字节长度表示的变长字段(表示字节长度)</p>
		 * <p>使用ASCII编码方式编码</p>
		 */
		LLVAR_CHAR,
		/**
		 * <p>2个字节长度表示的变长字段(表示字节长度)</p>
		 * <p>使用ASCII编码方式编码</p>
		 */
		LLLVAR_CHAR,
		/**
		 * <p>3个字节长度表示的变长字段(表示字节长度)</p>
		 * <p>使用ASCII编码方式编码</p>
		 */
		LLLLVAR_CHAR,
		/**
		 * <p>1个字节长度表示的变长字段(表示字节长度)</p>
		 * <p>使用BCD编码方式编码</p>
		 */
		LLVAR_BYTE_NUMERIC,
		/**
		 * <p>2个字节长度表示的变长字段(表示字节长度)</p>
		 * <p>使用BCD编码方式编码</p>
		 */
		LLLVAR_BYTE_NUMERIC,
		/**
		 * <p>3个字节长度表示的变长字段(表示字节长度)</p>
		 * <p>使用BCD编码方式编码</p>
		 */
		LLLLVAR_BYTE_NUMERIC,
		/**
		 * <p>3个字节长度表示的变长字段(表示字符长度)</p>
		 * <p>使用BCD编码方式编码</p>
		 */
		LLVAR_NUMERIC,
		/**
		 * <p>2个字节长度表示的变长字段(表示字符长度)</p>
		 * <p>使用BCD编码方式编码</p>
		 */
		LLLVAR_NUMERIC,
		/**
		 * <p>3个字节长度表示的变长字段(表示字符长度)</p>
		 * <p>使用BCD编码方式编码</p>
		 */
		LLLLVAR_NUMERIC
	}
	
}
