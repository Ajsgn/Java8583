package cn.ajsgn.test.example.factory;

import java.nio.charset.Charset;

import cn.ajsgn.common.java8583.core.Iso8583MessageFactory;
import cn.ajsgn.common.java8583.field.FillBlankStrategy;
import cn.ajsgn.common.java8583.field.Iso8583DataHeader;
import cn.ajsgn.common.java8583.field.Iso8583FieldType;

/**
 * 封装消息格式的工厂类</br>
 * 提供静态工厂方法，但每次都会new对象，所以，建议开发对生成的对象做缓存操作
 * @ClassName: BankFactory
 * @Description: 用于封装消息格式的工厂类
 * @author Ajsgn@foxmail.com
 * @date 2017年3月24日 上午10:52:38
 */
class BankFactory {
	
	/**
	 * 广发银行报文格式
	 * @Title: cgbankIso8583Factory
	 * @Description: 广发银行报文格式
	 * @return Iso8583Factory
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 上午11:07:38
	 */
	public static Iso8583MessageFactory getGDBIso8583Factory(){
		Iso8583MessageFactory factory = new Iso8583MessageFactory(2,false,Charset.forName("GBK"));
		Iso8583DataHeader dataHeaderType = new Iso8583DataHeader(
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,5),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,6),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8));
		factory.setDataHeader(dataHeaderType)
			   .set( 2, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
			   .set( 3, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
			   .set( 4, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,6))
			   .set(11, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
			   .set(12, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
			   .set(13, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
			   .set(14, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
			   .set(15, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
			   .set(22, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('0', false)))
			   .set(23, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
			   .set(25, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,1))
			   .set(26, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,1))
			   .set(32, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
			   .set(35, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
			   .set(36, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
			   .set(37, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,12))
			   .set(38, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,6))
			   .set(39, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,2))
			   .set(41, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,8))
			   .set(42, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,15))
			   .set(43, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,40))
			   .set(44, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR,0))
			   .set(46, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
			   .set(48, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0))
			   .set(49, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,3))
			   .set(52, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8))
			   .set(53, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8))
			   .set(54, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
			   .set(58, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
			   .set(60, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
			   .set(61, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
			   .set(62, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
			   .set(63, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
			   .set(64, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8));
		return factory;
	}
	
	/**
	 * 兴业银行报文格式
	 * @Title: getCIBIso8583Factory
	 * @Description: 兴业银行报文格式
	 * @return Iso8583Factory
	 * @author Ajsgn@foxmail.com
	 * @date 2017年3月24日 下午5:32:46
	 */
	public static Iso8583MessageFactory getCIBIso8583Factory(){
		Iso8583MessageFactory factory = new Iso8583MessageFactory(2,false,Charset.forName("GBK"));
		Iso8583DataHeader dataHeaderType = new Iso8583DataHeader(
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,5),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,6),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8));
		factory.setDataHeader(dataHeaderType)
		.set( 2, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
		.set( 3, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
		.set( 4, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,6))
		.set(11, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
		.set(12, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,3))
		.set(13, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
		.set(14, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
		.set(15, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
		.set(22, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('0', false)))
		.set(23, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2))
		.set(25, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,1))
		.set(26, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,1))
		.set(32, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
		.set(35, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
		.set(36, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('F', false)))
		.set(37, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,12))
		.set(38, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,6))
		.set(39, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,2))
		.set(41, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,8))
		.set(42, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,15))
		.set(44, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR,0))
		.set(48, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0))
		.set(49, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,3))
		.set(52, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8))
		.set(53, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8))
		.set(54, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
		.set(55, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
		.set(58, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
		.set(60, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('0', false)))
		.set(61, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0))
		.set(62, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,20))
		.set(63, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0))
		.set(64, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,8));
		return factory;
	}
	
	public static Iso8583MessageFactory getTestFactory(){
		Iso8583MessageFactory factory = new Iso8583MessageFactory(2,false,Charset.forName("GBK"));
		Iso8583DataHeader dataHeaderType = new Iso8583DataHeader(
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,5),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,6),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2),
				new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8));
		factory.setDataHeader(dataHeaderType)
			   .set( 2, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('A', false)))
			   .set( 3, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.CHAR,3))
			   .set( 4, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLVAR,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('B', false)))
			   .set(11, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,2).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('0', false)))
			   .set(12, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('D', false)))
			   .set(13, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.LLLVAR_NUMERIC,0).setFillBlankStrategy(FillBlankStrategy.rightAppendStrategy('E', false)))
			   .set(14, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.BINARY,8));
		
		factory.set(64, new Iso8583FieldType(Iso8583FieldType.FieldTypeValue.NUMERIC,8));
		return factory;
	}
	
}
