package com.icebreak.p2p.util.security;

public abstract class Algorithm
{
	/**
	 * 获得加密数据
	 * 
	 * @param data
	 * @return
	 */
	public abstract String getEncrypt(String data);

	/**
	 * 获得解密数据
	 * 
	 * @param data
	 * @return
	 */
	public abstract String getDecrypt(String data);
}
