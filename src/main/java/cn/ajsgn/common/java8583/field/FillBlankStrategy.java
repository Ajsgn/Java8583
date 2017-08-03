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
 * <p>填充策略</p>
 * <p>通常只有在使用BCD编码，并且数据长度为奇数时，才需要填充策略。字符型，ASCII编码不需要设置</p>
 * @ClassName: FillBlankStrategy
 * @Description: 填充策略
 * @author Ajsgn@foxmail.com
 * @date 2017年3月27日 上午10:06:01
 */
public class FillBlankStrategy {
	/**
	 * <p>启用左补位操作（即报文信息右对齐）?</p>
	 */
	private boolean leftAppend = false;
	/**
	 * <p>默认填充内容</p>
	 */
	private char value = ' ';
	/**
	 * <p>长度计算时，是否需要包含补位信息</p>
	 */
	private boolean lengthWithBlank = false;
	
	/**
	 * <p>私有化构造函数，防止创建错误的对象，通过静态工厂创建对象</p>
	 * @param leftAppend true，使用左填充，右对齐操作；false，使用右对齐，左填充策略
	 * @param value 填充字符
	 * @param lengthWithBlank 长度值计算是否包含填充内容
	 */
	private FillBlankStrategy(boolean leftAppend,char value,boolean lengthWithBlank){
		this.leftAppend = leftAppend;
		this.value = value;
		this.lengthWithBlank = lengthWithBlank;
	}
	
	/**
	 * <p>静态工厂方法，创建一个左补位，右对齐的补位策略</p>
	 * @Title: leftAppendStrategy
	 * @Description: 静态工厂方法，创建一个左补位，右对齐的补位策略
	 * @param value 补位内容
	 * @param lengthWithBlank 长度计算是否要包含补位内容
	 * @return FillBlankStrategy
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月27日 上午10:09:51
	 */
	public static FillBlankStrategy leftAppendStrategy(char value,boolean lengthWithBlank){
		return new FillBlankStrategy(true,value,lengthWithBlank);
	}
	
	/**
	 * <p>静态工厂方法，创建一个右补位，左对齐的补位策略</p>
	 * @Title: leftAppendInstance
	 * @Description: 静态工厂方法，创建一个左补位，右对齐的补位策略
	 * @param value 补位内容
	 * @param lengthWithBlank 长度计算是否要包含补位内容
	 * @return FillBlankStrategy
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月27日 上午10:13:10
	 */
	public static FillBlankStrategy rightAppendStrategy(char value,boolean lengthWithBlank){
		return new FillBlankStrategy(false,value,lengthWithBlank);
	}
	
	/**
	 * <p>获取当前补位方向，是否为左对齐</p>
	 * @Title: isLeftAppend
	 * @Description: 获取当前补位方向，是否为左对齐
	 * @return boolean 获取当前填充策略是否为左填充
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月27日 上午10:13:41
	 */
	public boolean isLeftAppend() {
		return leftAppend;
	}
	
	/**
	 * <p>获取补位的字符内容</p>
	 * @Title: getValue
	 * @Description: 获取补位的字符内容
	 * @return char获取填充字符
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月27日 上午10:14:05
	 */
	public char getValue() {
		return value;
	}
	
	/**
	 * <p>计算长度是否要包含填充字符内容</p>
	 * @Title: isLengthWithBlank
	 * @Description: 计算长度是否要包含填充字符内容
	 * @return boolean 计算长度是否包含填充位
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月27日 上午10:14:25
	 */
	public boolean isLengthWithBlank() {
		return lengthWithBlank;
	}
	
}
