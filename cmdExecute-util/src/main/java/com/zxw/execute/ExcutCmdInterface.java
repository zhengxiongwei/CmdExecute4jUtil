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
public interface ExcutCmdInterface {

	/**
	 * 执行命令
	 * 
	 * @param cmd
	 * @return Process
	 * @throws Exception
	 */
	public Process executeCmd(String cmd) throws Exception;

	/**
	 * 执行命令
	 * 
	 * @param cmd
	 * @param dirPath
	 * @return Process
	 * @throws Exception
	 */
	public Process executeCmd(String cmd, String dirPath) throws Exception;

	/**
	 * 执行命令脚本文件
	 * 
	 * @param filePath
	 *            脚本的路径
	 * @param dirPath
	 *            脚本所在目录的路径(脚本的父目录路径)
	 * @return
	 * @throws Exception
	 */
	public Process executeCmdByShFile(String filePath, String dirPath)
			throws Exception;

	/**
	 * 启动服务
	 * 
	 * @param name
	 */
	public Process startService(String name);

	/**
	 * 启动服务
	 * 
	 * @param serviceName
	 * @param executeUserName
	 */
	public Process startService(String serviceName, String executeUserName);

	/**
	 * 重启服务
	 * 
	 * @param name
	 */
	public Process reStartService(String name);

	/**
	 * 重启服务
	 * 
	 * @param serviceName
	 * @param executeUserName
	 */
	public Process reStartService(String serviceName, String executeUserName);

	/**
	 * 启动web服务
	 * 
	 * @param name
	 * @param location
	 */
	@Deprecated
	public Process startWebService(String name, String location);

	/**
	 * 启动web服务
	 * 
	 * @param name
	 * @param location
	 * @param executeUserName
	 */
	@Deprecated
	public Process startWebService(String name, String location,
			String executeUserName);

	/**
	 * 停止web服务
	 * 
	 * @param name
	 * @param location
	 */
	@Deprecated
	public Process shutdownWebService(String name, String location);

	/**
	 * 停止服务
	 * 
	 * @param name
	 */
	public Process shutdownService(String name);
}
