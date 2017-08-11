package cn.ajsgn.common.java8583.ext.tlv;

import java.util.List;

public interface TlvParser {
	
	public List<TlvObject> tlvParse(String data);
	
}
