package com.zxw.execute;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.zxw.util.Assert;

/**
 * <B>说 明</B>:
 * 
 * @author 作 者 名：郑雄伟<br/>
 *         E-mail ：zhengxiongwei@vrvmail.com.cn
 * 
 * @version 版 本 号：V1.0.<br/>
 *          创建时间：2017年7月5日 下午4:40:26
 */
public class ExcutCmdToolForWindow implements ExcutCmdInterface {
	private static Logger loger = Logger.getLogger(ExcutCmdToolForWindow.class);
	@SuppressWarnings("unused")
	private Map<String, Process> serviceProcessMap;
	@SuppressWarnings("unused")
	private String characterencoding;

	public ExcutCmdToolForWindow(Map<String, Process> serviceProcessMap,
			String characterencoding) {
		this.serviceProcessMap = serviceProcessMap;
		this.characterencoding = characterencoding;
	}

	/**
	 * 执行命令
	 * 
	 * @param cmd
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	public Process executeCmd(String cmd) throws Exception {
		return executeCmd(cmd, null);
	}

	/**
	 * 执行命令
	 * 
	 * @param cmd
	 * @param dirPath
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	public Process executeCmd(String cmd, String dirPath) throws Exception {
		Assert.isNotBlank(cmd, " cmd should not be null or empty ");
		String[] cmdArray = { "cmd.exe", "/c", cmd };
		ProcessBuilder processBuilder = new ProcessBuilder(cmdArray);
		if (StringUtils.isNotBlank(dirPath)) {
			File file = new File(dirPath);
			Assert.isTrue(file.exists(), " file of  the  dirPath not exist !");
			processBuilder.directory(file);
		}
		Process process = null;
		try {
			process = processBuilder.start();
			int exitValue = process.waitFor();
			if (0 != exitValue) {
				throw new ExcutCmdException("[" + cmd + "]execute fail");
			}
		} catch (IOException e) {
			loger.error("[" + cmd + "]execute fail,the exception:", e);
			throw e;
		} catch (InterruptedException e) {
			loger.error("[" + cmd + "]execute fail,the exception:", e);
			throw e;
		}
		return process;
	}

	/**
	 * 执行批处理文件
	 * 
	 * @param filePath
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	public Process executeCmdByShFile(String filePath, String dirPath)
			throws Exception {
		return executeCmd(filePath, dirPath);
	}

	@Override
	public Process startService(String name) {
		loger.error("Method is not implemented, can not be used !");
		return null;
	}

	@Override
	public Process startService(String serviceName, String executeUserName) {
		loger.error("Method is not implemented, can not be used !");
		return null;
	}

	@Override
	public Process reStartService(String name) {
		loger.error("Method is not implemented, can not be used !");
		return null;
	}

	@Override
	public Process reStartService(String serviceName, String executeUserName) {
		loger.error("Method is not implemented, can not be used !");
		return null;

	}

	@Override
	public Process startWebService(String name, String location) {
		loger.error("Method is not implemented, can not be used !");
		return null;
	}

	@Override
	public Process startWebService(String name, String location,
			String executeUserName) {
		loger.error("Method is not implemented, can not be used !");
		return null;
	}

	@Override
	public Process shutdownWebService(String name, String location) {
		loger.error("Method is not implemented, can not be used !");
		return null;
	}

	@Override
	public Process shutdownService(String name) {
		loger.error("Method is not implemented, can not be used !");
		return null;
	}

}
