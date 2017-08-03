package cn.ajsgn.test.example.factory;

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

	public static final Iso8583MessageFactory getTestFactory() {
		return SingletonHolder.TEST_ISO8583_FACTORY;
	}

}
