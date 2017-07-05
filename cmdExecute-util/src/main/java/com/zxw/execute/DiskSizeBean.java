package com.zxw.execute;

/**
 * <B>说 明</B>:
 * 
 * @author 作 者 名：郑雄伟<br/>
 *         E-mail ：zhengxiongwei@vrvmail.com.cn
 * 
 * @version 版 本 号：V1.0.<br/>
 *          创建时间：2017年4月10日 下午5:41:49
 */
public class DiskSizeBean {
	private Long diskTotalSize;
	private Long diskUsedSize;
	private Long diskAvailSize;
	private Double diskUsage;

	public Long getDiskTotalSize() {
		return diskTotalSize;
	}

	public void setDiskTotalSize(Long diskTotalSize) {
		this.diskTotalSize = diskTotalSize;
	}

	public Long getDiskUsedSize() {
		return diskUsedSize;
	}

	public void setDiskUsedSize(Long diskUsedSize) {
		this.diskUsedSize = diskUsedSize;
	}

	public Long getDiskAvailSize() {
		return diskAvailSize;
	}

	public void setDiskAvailSize(Long diskAvailSize) {
		this.diskAvailSize = diskAvailSize;
	}

	public Double getDiskUsage() {
		return diskUsage;
	}

	public void setDiskUsage(Double diskUsage) {
		this.diskUsage = diskUsage;
	}

	@Override
	public String toString() {
		return "DiskSizeBean [diskTotalSize=" + diskTotalSize
				+ ", diskUsedSize=" + diskUsedSize + ", diskAvailSize="
				+ diskAvailSize + ", diskUsage=" + diskUsage + "]";
	}

}
