package com.zxw.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.zxw.execute.DiskSizeBean;
import com.zxw.execute.MemorySizeBean;

/**
 * <B>说 明</B>:
 * 
 * @author 作 者 名：郑雄伟<br/>
 *         E-mail ：zhengxiongwei@vrvmail.com.cn
 * 
 * @version 版 本 号：V1.0.<br/>
 *          创建时间：2017年7月5日 下午4:40:26
 */
public class CmdExectueUtils {
	private static Logger log = Logger.getLogger(CmdExectueUtils.class);
	public static final String QUERY_PROCESS_CMD_LINE_PRONAME = "ps -C $PRONAME$  -o  %cpu,%mem,rss,pid";
	public static final String QUERY_PROCESS_CMD_LINE_PID = "ps -p $PID$  -o  %cpu,%mem,rss,pid";
	public static final String QUERY_CONNECT_CMD_LINE_PORT = "netstat -antp|awk -F ' ' '{print $4\"-\"$6\"-\"$NF}'|grep ':$PORT$\\b' |grep ESTABLISHED|wc -l";

	/**
	 * 获取返回结果中的数字
	 * 
	 * @param str
	 * @return 0：cpu使用率、1：内存、2:内存使用率、3:进程号
	 */
	public static List<String> parserDecimal(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
		Matcher mat_1 = pattern.matcher(str);
		List<String> result = null;
		while (mat_1.find()) {
			if (StringUtils.isNotBlank(mat_1.group())) {
				if (result == null) {
					result = new ArrayList<String>();
				}
				result.add(mat_1.group());
			}
		}
		return result;
	}

	/**
	 * 获取服务的监控信息
	 * 
	 * @param proName
	 * @return 0：cpu使用率、1：内存、2:内存使用率、3:进程号
	 * @throws Exception
	 */
	public static Object[] getServiceMonitorInfoByPorName(String proName)
			throws Exception {
		String cmd = QUERY_PROCESS_CMD_LINE_PRONAME;
		Process process = null;
		BufferedReader reader = null;
		try {
			process = ExcutCmdScriptUtils.initialization().executeCmd(
					cmd.replace("$PRONAME$", proName));
			reader = new BufferedReader(new InputStreamReader(
					process.getInputStream(), "utf-8"));
			String line = null;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (i != 0) {
					List<String> re_list = parserDecimal(line);
					return re_list == null ? null : re_list.toArray();
				}
				i++;
			}
		} catch (Exception e) {
			// log.error("[" + cmd.replace("$PRONAME$", proName) +
			// "]获取失败！失败原因：", e);
			throw e;
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
		return null;
	}

	/**
	 * 获取服务的监控信息
	 * 
	 * @param proName
	 * @return 0：cpu使用率、1：内存、2:内存使用率、3:进程号
	 * @throws Exception
	 */
	public static Object[] getServiceMonitorInfobyPid(String pid)
			throws Exception {
		String cmd = QUERY_PROCESS_CMD_LINE_PID;
		Process process = null;
		BufferedReader reader = null;
		try {
			process = ExcutCmdScriptUtils.initialization().executeCmd(
					cmd.replace("$PID$", pid));
			reader = new BufferedReader(new InputStreamReader(
					process.getInputStream(), "utf-8"));
			String line = null;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (i != 0) {
					List<String> re_list = parserDecimal(line);
					return re_list == null ? null : re_list.toArray();
				}
				i++;
			}
		} catch (Exception e) {
			log.error("[" + cmd.replace("$PID$", pid) + "]获取失败！失败原因：", e);
			throw e;
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
		return null;
	}

	public static int getServiceConnection(int port) {
		String cmd = QUERY_CONNECT_CMD_LINE_PORT;// 查看缓存服务端口链接数量
		Process pro = null;
		int connection = 0;
		try {
			pro = ExcutCmdScriptUtils.initialization().executeCmd(
					cmd.replace("$PORT$", port + ""));
			pro.waitFor();
			InputStream in = pro.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = read.readLine()) != null) {
				connection = Integer.valueOf(line);
			}
			return connection;
		} catch (Exception e) {
			log.error("[" + cmd.replace("$PORT$", port + "") + "]", e);
		}
		return connection;
	}

	/**
	 * 获取文件或文件夹大小 将其包装为File
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileSizeCopy(String filePath) {
		String fileSize = null;
		File file = new File(filePath);
		if (file.exists()) {
			if (file.isDirectory()) {
				Long length = file.getTotalSpace() / 1024;
				fileSize = length.toString();
			} else {
				Long length = file.length() / 1024;
				fileSize = length.toString();
			}
		} else {
			log.error("该文件或者目录不存在！");
		}

		return fileSize;
	}

	/**
	 * 获取文件或者目录占用磁盘大小
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileSize(String filePath) {
		String fileSize = "";
		try {
			// Process process =
			// ExcutCmdScriptUtils.initialization().executeCmd("du -sh", file);
			Process process = ExcutCmdScriptUtils.initialization().executeCmd(
					"du --max-depth=0 " + filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream(), "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileSize = line;
			}
			fileSize = fileSize.split(File.separator)[0].trim();
		} catch (Exception e) {
			log.error("获取文件或者目录占用磁盘大小的命令执行失败！", e);
		}
		return fileSize;
	}

	public static MemorySizeBean getMemorySizeInfo() {
		MemorySizeBean memorySizeBean = new MemorySizeBean();
		try {

			Process process = ExcutCmdScriptUtils.initialization().executeCmd(
					"free");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream(), "utf-8"));
			String line = null;
			int l = 0;
			float totalLong = 0;
			float usedLong = 0;
			float freeLong = 0;
			float sharedLong = 0;
			float buffersLong = 0;
			float cachedLong = 0;
			float memoryUsage = 0;
			while ((line = reader.readLine()) != null) {
				l++;
				if (l == 2) {
					String[] test = line.trim().split("\\s+");
					if (test.length > 1) {
						if (isNumeric(test[0])) {
							totalLong = Long.valueOf(test[0]);
							usedLong = Long.valueOf(test[1]);
							freeLong = Long.valueOf(test[2]);
							sharedLong = Long.valueOf(test[3]);
							buffersLong = Long.valueOf(test[4]);
							cachedLong = Long.valueOf(test[5]);
						} else if (isNumeric(test[1])) {
							totalLong = Long.valueOf(test[1]);
							usedLong = Long.valueOf(test[2]);
							freeLong = Long.valueOf(test[3]);
							sharedLong = Long.valueOf(test[4]);
							buffersLong = Long.valueOf(test[5]);
							cachedLong = Long.valueOf(test[6]);
						}
					}
				}
			}

			memoryUsage = 1
					- (freeLong + sharedLong + buffersLong + cachedLong)
					/ totalLong;
			memorySizeBean.setMemoryTotalSize(totalLong);
			memorySizeBean.setMemoryUsedSize(usedLong);
			memorySizeBean.setMemoryfreeSize(freeLong);
			memorySizeBean.setMemoryUsage(memoryUsage);
			log.info("【磁盘信息】:" + memorySizeBean.toString());
		} catch (Exception e) {
			log.error("获取内存大小的命令执行失败！", e);
		}
		return memorySizeBean;

	}

	/**
	 * 获取磁盘大小信息
	 * 
	 * @return
	 */
	public static DiskSizeBean getDiskSizeInfo() {
		DiskSizeBean diskSizeBean = new DiskSizeBean();
		try {
			Process process = ExcutCmdScriptUtils.initialization().executeCmd(
					"df");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream(), "utf-8"));
			String line = null;
			int l = 0;
			Long totalLong = 0l;
			Long usedLong = 0l;
			Long availLong = 0l;
			Double usage = 0d;
			while ((line = reader.readLine()) != null) {
				l++;
				if (l > 1) {
					String[] test = line.trim().split("\\s+");
					if (test.length > 1) {
						if (isNumeric(test[0])) {
							log.info("test[0]：" + test[0]);
							totalLong += Long.valueOf(test[0]);
							usedLong += Long.valueOf(test[1]);
							availLong += Long.valueOf(test[2]);
							usage += Long.valueOf(test[3].split("%")[0]);
						} else if (isNumeric(test[1])) {
							log.info("test[1]：" + test[1]);
							totalLong += Long.valueOf(test[1]);
							usedLong += Long.valueOf(test[2]);
							availLong += Long.valueOf(test[3]);
							usage += Long.valueOf(test[4].split("%")[0]);
						}
					}
				}
			}
			diskSizeBean.setDiskTotalSize(totalLong);
			diskSizeBean.setDiskUsedSize(usedLong);
			diskSizeBean.setDiskAvailSize(availLong);
			diskSizeBean.setDiskUsage(usage);

			log.info("【磁盘信息】:" + diskSizeBean.toString());
		} catch (Exception e) {
			log.error("获取磁盘大小的命令执行失败！", e);
		}
		return diskSizeBean;
	}

	/**
	 * 获取文件目录占用磁盘总大小
	 * 
	 * @return
	 */
	public static String getFileTotalSize() {
		String totalSize = "";
		try {
			Process process = ExcutCmdScriptUtils.initialization().executeCmd(
					"df");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream(), "utf-8"));
			String line = null;
			int l = 0;
			Long iLong = 0l;
			while ((line = reader.readLine()) != null) {
				l++;
				if (l > 1) {
					String[] test = line.trim().split("\\s+");
					if (test.length > 1) {
						if (isNumeric(test[0])) {
							log.info("test[0]：" + test[0]);
							iLong += Long.valueOf(test[0]);
						} else if (isNumeric(test[1])) {
							log.info("test[1]：" + test[1]);
							iLong += Long.valueOf(test[1]);
						}
					}
				}
			}
			totalSize = String.valueOf(iLong);
		} catch (Exception e) {
			log.error("获取文件或者目录占用磁盘大小的命令执行失败！", e);
		}
		return totalSize;
	}

	/**
	 * 判断字符串是否为数字串
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
