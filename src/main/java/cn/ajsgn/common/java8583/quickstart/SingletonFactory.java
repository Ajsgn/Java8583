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

import cn.ajsgn.common.java8583.core.Iso8583MessageFactory;

/**
 * quick start
 * @ClassName: SingletonBankFactory
 * @Description: quick start
 * @author Ajsgn@foxmail.com
 * @date 2017年8月3日 下午4:31:17
 */
public class SingletonFactory {
	
	/**
	 * 对象持有
	 * @ClassName: SingletonHolder
	 * @Description: 对象持有
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月3日 下午4:31:57
	 */
	private static class SingletonHolder {
		private static final Iso8583MessageFactory QUICK_START = QuickStartFactory.forQuickStart();
	}

	private SingletonFactory() {
	}
	
	/**
	 * quick start
	 * @Title: getCIBIso8583Factory
	 * @Description: quick start
	 * @return Iso8583MessageFactory
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月3日 下午4:32:18
	 */
	public static final Iso8583MessageFactory forQuickStart() {
		return SingletonHolder.QUICK_START;
	}


}
