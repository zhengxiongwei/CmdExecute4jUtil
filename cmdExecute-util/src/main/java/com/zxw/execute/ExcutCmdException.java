package com.zxw.execute;

/**
 * <B>说 明</B>:
 * 
 * @author 作 者 名：郑雄伟<br/>
 *         E-mail ：zhengxiongwei@vrvmail.com.cn
 * 
 * @version 版 本 号：V1.0.<br/>
 *          创建时间：2017年7月5日 下午4:40:26
 */
public class ExcutCmdException extends Exception {

	private static final long serialVersionUID = 3197410573750421819L;

	public ExcutCmdException() {
		super();
	}

	public ExcutCmdException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExcutCmdException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcutCmdException(String message) {
		super(message);
	}

	public ExcutCmdException(Throwable cause) {
		super(cause);
	}

}
