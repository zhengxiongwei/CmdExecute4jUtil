package com.zxw.execute;

/**
 * <B>说 明</B>:
 * 
 * @author 作 者 名：郑雄伟<br/>
 *         E-mail ：zhengxiongwei@vrvmail.com.cn
 * 
 * @version 版 本 号：V1.0.<br/>
 *          创建时间：2017年4月10日 下午5:48:53
 */
public class MemorySizeBean {
	private float memoryTotalSize;
	private float memoryUsedSize;
	private float memoryfreeSize;
	private float memoryUsage;
	
	public float getMemoryTotalSize() {
		return memoryTotalSize;
	}

	public void setMemoryTotalSize(float memoryTotalSize) {
		this.memoryTotalSize = memoryTotalSize;
	}

	public float getMemoryUsedSize() {
		return memoryUsedSize;
	}

	public void setMemoryUsedSize(float memoryUsedSize) {
		this.memoryUsedSize = memoryUsedSize;
	}

	public float getMemoryfreeSize() {
		return memoryfreeSize;
	}

	public void setMemoryfreeSize(float memoryfreeSize) {
		this.memoryfreeSize = memoryfreeSize;
	}

	public double getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(float memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	@Override
	public String toString() {
		return "MemorySizeBean [memoryTotalSize=" + memoryTotalSize
				+ ", memoryUsedSize=" + memoryUsedSize + ", memoryfreeSize="
				+ memoryfreeSize + ", memoryUsage=" + memoryUsage + "]";
	}

}
