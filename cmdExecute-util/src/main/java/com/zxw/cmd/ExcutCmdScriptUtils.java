package com.zxw.cmd;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zxw.execute.ExcutCmdInterface;
import com.zxw.execute.ExcutCmdToolForLinux;
import com.zxw.execute.ExcutCmdToolForWindow;
import com.zxw.util.SystemUtils;

/**
 * <B>说 明</B>:执行操作系统命令或脚本(批处理bat或shell)工具类
 * 
  * @author 作 者 名：郑雄伟<br/>
 *         E-mail ：zhengxiongwei@vrvmail.com.cn
 * 
 * @version 版 本 号：V1.0.<br/>
 *          创建时间：2017年7月5日 下午4:40:26
 */
public class ExcutCmdScriptUtils {
	private static Logger loger = Logger.getLogger(ExcutCmdScriptUtils.class);
	private static Map<String, Process> serviceProcessMap = new HashMap<String, Process>();
	private static ExcutCmdInterface cmdInterface = null;
	private static String characterencoding = "utf-8";
	private static boolean have_Changed = false;

	private ExcutCmdScriptUtils() {
	}

	public static ExcutCmdInterface initialization() {
		String osName = SystemUtils.getOsName();
		if (cmdInterface == null || have_Changed) {
			if (osName != null && osName.toLowerCase().indexOf("windows") > -1) {
				cmdInterface = new ExcutCmdToolForWindow(serviceProcessMap,
						characterencoding);
			} else if (osName != null
					&& osName.toLowerCase().indexOf("linux") > -1) {
				cmdInterface = new ExcutCmdToolForLinux(serviceProcessMap,
						characterencoding);
			} else {
				// TODO 待完善
				loger.error("osName[" + osName + "] haven't an Adapter Class!");
			}
			have_Changed = false;
		}
		return cmdInterface;
	}

	public static ExcutCmdInterface initialization(String characterencodingx) {
		changeCharacterencoding(characterencodingx);
		return initialization();
	}

	private static void changeCharacterencoding(String characterencodingx) {
		characterencoding = characterencodingx;
		have_Changed = true;
	}
}
