package com.toolq.helper.jce;

import com.qq.jce.wup.UniPacket;
import com.qq.taf.Jce;
import com.toolq.helper.logger.TLog;
import com.toolq.resource.TString;

/**
 * @author luoluo
 * @date 2020/10/31 0:38
 */
public final class JceHelper {
	public static <T> T decodePacket(byte[] src, String FuncName, T t){
		try{
			if(!(t instanceof Jce)) {
				TLog.INSTANCE.warn(TString.jce_packet_type_error.get());
				return null;
			}
			UniPacket uniPacket = decodePacket(src);
			if(uniPacket != null) {
				return uniPacket.getByClass(FuncName, t);
			}
		}catch (Exception e){
			TLog.INSTANCE.error(e);
		}
		return null;
	}

	public static UniPacket decodePacket(byte[] src){
		try{
			UniPacket uniPacket = new UniPacket(true);
			uniPacket.setEncodeName("UTF-8");
			uniPacket.decode(src);
			return uniPacket;
		}catch (Exception e){
			TLog.INSTANCE.error(e);
			return null;
		}
	}

	public static <T> byte[] encodePacket(T t, String ServantName, String funcName, String mapName, int requestId){
		try{
			if(!(t instanceof Jce)) {
				TLog.INSTANCE.warn(TString.jce_packet_type_error.get());
				return null;
			}
			UniPacket uniPacket = new UniPacket(true);
			uniPacket.setEncodeName("UTF-8");
			uniPacket.useVersion3();
			uniPacket.setFuncName(funcName);
			uniPacket.setServantName(ServantName);
			uniPacket.put(mapName, t);
			uniPacket.setRequestId(requestId);
			return uniPacket.encode();
		}catch (Exception e){
			TLog.INSTANCE.error(e);
			return null;
		}
	}

	public static byte[] encodePacket(Jce<?>[] jce, String ServantName, String funcName, String[] mapName, int requestId){
		try {
			UniPacket uniPacket = new UniPacket(true);
			uniPacket.setEncodeName("UTF-8");
			uniPacket.useVersion3();
			uniPacket.setFuncName(funcName);
			uniPacket.setServantName(ServantName);
			for (int i = 0;i < jce.length; i++) {
				uniPacket.put(mapName[i], jce[i]);
			}
			uniPacket.setRequestId(requestId);
			return uniPacket.encode();
		} catch (Exception e) {
			TLog.INSTANCE.error(e);
			return null;
		}
	}
}
