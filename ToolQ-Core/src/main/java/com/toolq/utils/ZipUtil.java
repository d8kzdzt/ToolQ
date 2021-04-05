package com.toolq.utils;

import com.toolq.helper.logger.TLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author luoluo
 * @date 2020/10/7 4:11
 */
public class ZipUtil {
	public static byte[] uncompress(byte[] inputByte) {
		int len = 0;
		Inflater infl = new Inflater();
		infl.setInput(inputByte);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] outByte = new byte[1024];
		try {
			while (!infl.finished()) {
				len = infl.inflate(outByte);
				if (len == 0) {
					break;
				}
				bos.write(outByte, 0, len);
			}
			infl.end();
		} catch (Exception e) {
			TLog.INSTANCE.warn("uncompress", e);
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				TLog.INSTANCE.warn("uncompress finally", e);
			}
		}
		return bos.toByteArray();
	}

	public static byte[] compress(byte[] inputByte) {
		int len = 0;
		Deflater defl = new Deflater();
		defl.setInput(inputByte);
		defl.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] outputByte = new byte[1024];
		try {
			while (!defl.finished()) {
				len = defl.deflate(outputByte);
				bos.write(outputByte, 0, len);
			}
			defl.end();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				TLog.INSTANCE.warn("compress finally", e);
			}
		}
		return bos.toByteArray();
	}
}
