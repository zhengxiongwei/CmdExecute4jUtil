package com.zxw.util; 

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.Charset;
import org.apache.commons.lang3.StringUtils;


/** 
 *   <B>说       明</B>:
 *
 * @author  作  者  名：郑雄伟<br/>
 *		    E-mail ：zhengxiongwei@vrvmail.com.cn
 
 * @version 版   本  号：V1.0.<br/>
 *          创建时间：2017年7月5日 下午4:58:37 
 */
public class SystemUtils {
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private static String lp;
	public static final String LINESEPARATOR;

	static {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(2);
		PrintWriter writer = new PrintWriter(bos, true);
		writer.println();
		lp = new String(bos.toByteArray());
		writer.close();
		if (lp == null) {
			lp = new String(new byte[] { 13, 10 });
		}

		LINESEPARATOR = lp;
	}

	public static String getJavaVMName() {
		return System.getProperty("java.vm.name");
	}

	public static String getJavaVMVersion() {
		return System.getProperty("java.vm.version");
	}

	public static String getJavaVMVendor() {
		return System.getProperty("java.vm.vendor");
	}

	public static String getJavaVersion() {
		return System.getProperty("java.version");
	}

	public static String getTempDir() {
		return System.getProperty("java.io.tmpdir");
	}

	public static String getOsName() {
		return System.getProperty("os.name");
	}

	public static String getOsArch() {
		return System.getProperty("os.arch");
	}

	public static String getOsVersion() {
		return System.getProperty("os.version");
	}

	public static String getUserName() {
		return System.getProperty("user.name");
	}

	public static String getJavaHome() {
		return System.getProperty("java.home");
	}

	public static String getArchDataModel() {
		return System.getProperty("sun.arch.data.model");
	}

	public static String getUserLanguage() {
		return System.getProperty("user.language");
	}

	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}

	public static int getProcessorCount() {
		return Runtime.getRuntime().availableProcessors();
	}

	public static long getFreeMemory() {
		return Runtime.getRuntime().freeMemory();
	}

	public static long getMaxMemory() {
		return Runtime.getRuntime().maxMemory();
	}

	public static long getTotalMemory() {
		return Runtime.getRuntime().totalMemory();
	}

	/*public static void printAllStackTrack() {
		Map stMap = Thread.getAllStackTraces();
		for (Thread thread : stMap.keySet()) {
			StackTraceElement[] elements = (StackTraceElement[]) stMap
					.get(thread);
			System.out.println(thread);
			for (StackTraceElement element : elements)
				System.out.println("\t" + element);
		}
	}*/

	public static long getJavaVMUptime() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		if (runtimeMXBean != null) {
			return runtimeMXBean.getUptime();
		}

		return -1L;
	}

	public static long getJavaVMStartTime() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		if (runtimeMXBean != null) {
			return runtimeMXBean.getStartTime();
		}

		return -1L;
	}

	public static boolean checkOs(String osName) {
		Assert.isNotBlank(osName, "输入参数[osName]为空！");
		osName = osName.toLowerCase();
		String os = getOsName();
		if (StringUtils.isBlank(os)) {
			throw new IllegalArgumentException("获取当前操作系统出错了！");
		}
		os = os.toLowerCase();

		return (os.indexOf(osName) != -1);
	}

	public static boolean checkOs(String osName, String osArch) {
		Assert.isNotBlank(osName, "输入参数[osName]为空！");
		Assert.isNotBlank(osArch, "输入参数[osArch]为空！");
		osName = osName.toLowerCase();
		osArch = osArch.toLowerCase();
		String os = getOsName();
		if (StringUtils.isBlank(os)) {
			throw new IllegalArgumentException("获取当前操作系统出错了！");
		}
		String arch = getOsArch();
		if (StringUtils.isBlank(osArch)) {
			throw new IllegalArgumentException("获取当前操作系统位数出错了！");
		}
		os = os.toLowerCase();
		arch = arch.toLowerCase();

		return ((os.indexOf(osName) != -1) && (arch.indexOf(osArch) != -1));
	}
}
 