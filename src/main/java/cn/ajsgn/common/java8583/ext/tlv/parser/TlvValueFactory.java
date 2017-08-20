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

import cn.ajsgn.common.java8583.ext.tlv.TlvValue;

/**
 * <p>TlvVlue做了包访问限制，使用者不需要关注实现，调用不依赖实现</p>
 * <p>如果需要做写入动作，创建对象需要通过该工厂类来创建</p>
 * @ClassName: TlvValueFactory
 * @Description: 用于创建TlvValue对象的Factory类
 * @author Ajsgn@foxmail.com
 * @date 2017年8月20日 上午10:36:35
 */
public class TlvValueFactory {
	
	/**
	 * 创建一个55域值对象
	 * @Title: field55TlvValueInstance
	 * @Description: 创建一个55域值对象
	 * @param tagName 域key
	 * @param tagValue 域值
	 * @return TlvValue 返回构建结果对象
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月20日 上午10:40:04
	 */
	public static TlvValue field55TlvValueInstance(String tagName,String tagValue){
		return new Field55TlvValue(tagName, tagValue);
	}
	
}
