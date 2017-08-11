package cn.ajsgn.common.java8583.ext.tlv;

public interface TlvObject {
	
	public abstract String getTag();
	
	public abstract void setTag();
	
	public abstract int getLength();
	
	public abstract String getValue();
	
	public abstract void setValue();
	
}
