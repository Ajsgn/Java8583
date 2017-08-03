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

public class SingletonBankFactory {

	private static class SingletonHolder {
		private static final Iso8583MessageFactory CIB_ISO8583_FACTORY = BankFactory.getCIBIso8583Factory();
		private static final Iso8583MessageFactory GDB_ISO8583_FACTORY = BankFactory.getGDBIso8583Factory();
		private static final Iso8583MessageFactory TEST_ISO8583_FACTORY = BankFactory.getTestFactory();
	}

	private SingletonBankFactory() {
	}

	public static final Iso8583MessageFactory getCIBIso8583Factory() {
		return SingletonHolder.CIB_ISO8583_FACTORY;
	}

	public static final Iso8583MessageFactory getGDBIso8583Factory() {
		return SingletonHolder.GDB_ISO8583_FACTORY;
	}
	
	public static final Iso8583MessageFactory getTestIso8583Factory() {
		return SingletonHolder.TEST_ISO8583_FACTORY;
	}

}
