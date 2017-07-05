package com.zxw.execute;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.zxw.util.Assert;

/**
 * <B>说 明</B>:linux命令执行工具
 * 
 * @author 作 者 名：郑雄伟<br/>
 *         E-mail ：zhengxiongwei@vrvmail.com.cn
 * 
 * @version 版 本 号：V1.0.<br/>
 *          创建时间：2017年7月5日 下午4:40:26
 */
public class ExcutCmdToolForLinux implements ExcutCmdInterface {
	private static Logger loger = Logger.getLogger(ExcutCmdToolForLinux.class);
	private Map<String, Process> serviceProcessMap;
	@SuppressWarnings("unused")
	private String characterencoding;

	public ExcutCmdToolForLinux(Map<String, Process> serviceProcessMap,
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
	 * @throws ExcutCmdException
	 */
	public Process executeCmd(String cmd) throws IOException,
			InterruptedException, ExcutCmdException {
		Assert.isNotBlank(cmd, " cmd should not be null or empty ");
		return executeCmd(cmd, null);
	}

	/**
	 * 执行命令
	 * 
	 * @param cmd
	 * @param dirPath
	 * @return Process
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExcutCmdException
	 */
	public synchronized Process executeCmd(String cmd, String dirPath)
			throws IOException, InterruptedException, ExcutCmdException {
		Assert.isNotBlank(cmd, " cmd should not be null or empty ");
		String[] cmdArray = { "/bin/sh", "-c", cmd };
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
				showErrorMsg(process);
				throw new ExcutCmdException("[" + cmd + "]execute fail");
			}
		} catch (IOException e) {
			loger.error("[" + cmd + "]execute fail,the exception:", e);
			throw e;
		} catch (InterruptedException e) {
			loger.error("[" + cmd + "]execute fail,the exception:", e);
			throw e;
		} catch (ExcutCmdException e) {
			throw e;
		}
		return process;
	}

	/**
	 * 执行shell文件
	 * 
	 * @param filePath
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExcutCmdException
	 */
	public Process executeCmdByShFile(String filePath, String dirPath)
			throws IOException, InterruptedException, ExcutCmdException {
		Assert.isNotBlank(filePath, " filePath should not be null or empty ");
		Assert.isNotBlank(dirPath, " dirPath should not be null or empty ");
		loger.info("[" + filePath + "][" + dirPath + "]");
		Process process = null;
		try {
			executeChmodFile("a+x", new String[] { filePath });
			process = executeCmd(filePath, dirPath);
		} catch (IOException e) {
			loger.error("execute fail,the exception:", e);
			throw e;
		}
		return process;
	}

	/**
	 * 改变文件的权限
	 * 
	 * @param cmd
	 * @param destFilePath
	 * @throws InterruptedException
	 * @throws ExcutCmdException
	 */
	private void executeChmodFile(String cmd, String[] destFilePath)
			throws InterruptedException, ExcutCmdException {
		Assert.isNotBlank(cmd, " cmd should not be null or empty ");
		Assert.notEmpty(destFilePath, " destFilePath should not be empty ");
		StringBuilder stb = new StringBuilder("chmod  ");
		stb.append(cmd);
		stb.append(" " + getStringFormStringArrays(destFilePath));
		try {
			executeCmd(stb.toString());
		} catch (IOException e) {
			loger.error("[" + stb.toString() + "]execute fail,the exception:",
					e);
		}
	}

	/**
	 * 标准服务启动类
	 * 
	 * @param name
	 * @param location
	 * @param isVrv
	 *            是否以vrv用户启动
	 */
	public Process startService(String serviceName, String executeUserName) {
		loger.info("Method execution start...");
		String cmd = null;
		if (StringUtils.isBlank(executeUserName)) { // 默认和当前服务同用户启动
			cmd = "service " + serviceName + " start";
		} else {
			cmd = "su " + executeUserName + " service " + serviceName
					+ " start";
		}
		Process pro = null;
		try {
			pro = serviceProcessMap.get(serviceName);
			if (pro != null) { // 把旧的子进程关闭
				pro.destroy();
			}
			pro = executeCmd(cmd);
			int exitValue = pro.waitFor();
			if (0 != exitValue) {
				loger.error("[" + serviceName + "]service start fail");
				throw new ExcutCmdException("[" + cmd + "]execute fail");
			} else {
				loger.info("[" + serviceName + "]service started ");
			}
			serviceProcessMap.put(serviceName, pro);
		} catch (Exception e) {
			loger.error("[" + serviceName + "]service start fail", e);
		}
		loger.info(" Method execution is completed ");
		return pro;
	}

	/**
	 * 标准服务启动类(与本服务同用户启动)
	 * 
	 * @param name
	 * @param location
	 */
	public Process startService(String name) {
		return startService(name, null);
	}

	/**
	 * 标准服务重启
	 * 
	 * @param name
	 * @param location
	 * @param isVrv
	 *            是否以vrv用户启动
	 */
	public Process reStartService(String serviceName, String executeUserName) {
		loger.info("Method execution start...");
		String cmd = null;
		if (StringUtils.isBlank(executeUserName)) { // 默认和当前服务同用户启动
			cmd = "service " + serviceName + " restart ";
		} else {
			cmd = "su " + executeUserName + " service " + serviceName
					+ " restart";
		}
		Process pro = null;
		try {
			pro = serviceProcessMap.get(serviceName);
			if (pro != null) { // 把旧的子进程关闭
				pro.destroy();
			}
			pro = executeCmd(cmd);
			int exitValue = pro.waitFor();
			if (0 != exitValue) {
				loger.error("[" + serviceName + "]service start fail");
				throw new ExcutCmdException("[" + cmd + "]execute fail");
			} else {
				loger.info("[" + serviceName + "]service started ");
			}
			serviceProcessMap.put(serviceName, pro);
		} catch (Exception e) {
			loger.error("[" + serviceName + "]service start fail", e);
		}
		loger.info(" Method execution is completed ");
		return pro;
	}

	/**
	 * 标准服务重启(与本服务同用户启动)
	 * 
	 * @param name
	 * @param location
	 */
	public Process reStartService(String name) {
		return reStartService(name, null);
	}

	/**
	 * WEB服务启动类
	 * 
	 * @param name
	 * @param location
	 * @param executeUserName
	 */
	@Deprecated
	public Process startWebService(String name, String location,
			String executeUserName) {
		loger.info("Method execution start...");
		File file = new File(location);
		Process pro = null;
		if (file.exists()) {
			try {
				pro = serviceProcessMap.get(name);
				if (pro != null) { // 把旧的子进程关闭
					pro.destroy();
				}
				String cmd = null;
				if (StringUtils.isBlank(executeUserName)) { // 默认和当前服务同用户启动
					cmd = "sh startup.sh";
				} else {
					cmd = "su " + executeUserName + " startup.sh";
				}

				pro = executeCmd(cmd, location);
				serviceProcessMap.put(name, pro);
				int exitValue = pro.waitFor();
				if (0 != exitValue) {
					loger.error("[" + name + "]service start fail");
					throw new ExcutCmdException("[" + cmd + "]execute fail");
				} else {
					loger.info("[" + name + "]service started ");
				}
			} catch (Exception e) {
				loger.error("[" + name + "]service start fail", e);
			}
		}
		loger.info(" Method execution is completed ");
		return pro;
	}

	/**
	 * WEB服务启动类(与本服务同用户启动)
	 * 
	 * @param name
	 * @param port
	 * @param location
	 */
	@Deprecated
	public Process startWebService(String name, String location) {
		return startWebService(name, location, null);
	}

	/**
	 * WEB服务停止
	 * 
	 * @param name
	 * @param location
	 */
	@Deprecated
	public Process shutdownWebService(String name, String location) {
		loger.info("Method execution start...");
		String cmd = "sh shutdown.sh";
		Process pro = null;
		try {
			pro = serviceProcessMap.get(name);
			if (pro != null) { // 把旧的子进程关闭
				serviceProcessMap.remove(name);
				pro.destroy();
			}
			pro = executeCmd(cmd, location);
			int exitValue = pro.waitFor();
			if (0 != exitValue) {
				loger.error("[" + name + "]service shuntdown fail");
				throw new ExcutCmdException("[" + cmd + "]execute fail");
			} else {
				loger.info("[" + name + "]service shuntdown ");
			}
		} catch (Exception e) {
			loger.error("[" + name + "]service start fail", e);
		}

		loger.info(" Method execution is completed ");
		return pro;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private String getStringFormStringArrays(String[] s) {
		return Arrays.toString(s).substring(1, Arrays.toString(s).length() - 1)
				.replace(",", "");
	}

	/**
	 * 服务停止
	 * 
	 * @param name
	 * @param location
	 */
	public Process shutdownService(String name) {
		loger.info("Method execution start...");

		String cmd = "service " + name + " stop ";
		Process pro = null;
		try {
			pro = serviceProcessMap.get(name);
			if (pro != null) { // 把旧的子进程关闭
				serviceProcessMap.remove(name);
				pro.destroy();
			}
			pro = executeCmd(cmd);
			int exitValue = pro.waitFor();
			if (0 != exitValue) {
				loger.error("[" + name + "]service shuntdown fail");
				throw new ExcutCmdException("[" + cmd + "]execute fail");
			} else {
				loger.info("[" + name + "]service shuntdown ");
			}
		} catch (Exception e) {
			loger.error("[" + name + "]service start fail", e);
		}

		loger.info(" Method execution is completed ");
		return pro;
	}

	private void showErrorMsg(Process process) {
		if (process != null) {
			try {
				StringBuilder builder = new StringBuilder();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(process.getErrorStream(), "utf-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
					loger.error("错误信息为：[" + line + "]");
				}
			} catch (Exception e) {
				loger.error(e);
			}
		}
	}
}
