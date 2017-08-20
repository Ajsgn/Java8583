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
package cn.ajsgn.common.java8583.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>Io读取工具类</p>
 * @ClassName: IoUtil
 * @Description: Io读取工具类
 * @author Ajsgn@foxmail.com
 * @date 2017年8月20日 上午12:09:31
 */
public class IoUtil {
	
	/**
	 * <p>读取一段数据内容</p>
	 * @Title: read
	 * @Description: 读取一段数据内容
	 * @param is 数据来源
	 * @param length 读取长度
	 * @return byte[] 读取数据结果
	 * @author Ajsgn@foxmail.com
	 * @date 2017年8月20日 上午12:10:07
	 */
	public static byte[] read(InputStream is,int length) throws IOException{
		byte[] bs = new byte[length];
		is.read(bs);
		return bs;
	}
	
}
