package com.toolq.helper.bot.packet;

import com.toolq.helper.logger.TLog;
import com.toolq.helper.packet.JceCouple;
import com.toolq.helper.thread.ThreadManager;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 同一个Waiter禁止重复使用，以免出现线程安全问题
 */
public abstract class Waiter implements IWaiter {
	public Waiter(String cmd) {
		if(cmd == null) throw new RuntimeException("WaiterCmd can't be null.");
		this.cmd = cmd;
	}

	/**
	 * 包的名字
	 */
	private final String cmd;
	public final BlockingQueue<Boolean> queue = new ArrayBlockingQueue<>(1);
	/**
	 * 包的内容
	 */
	public Packet packet;
	public final long id = new Random().nextLong() + (System.currentTimeMillis() / 1000);
	/**
	 * 是否已经接到了包
	 */
	private boolean used = false;

	public boolean wait(Object randomId) {
		return wait(randomId, 3 * 1000);
	}

	/**
	 * 堵塞线程，开始等包
	 *
	 * @param randomId 等包随机id
	 * @param timeout 等包超时时间
	 * @return 是否等包成功
	 */
	public boolean wait(Object randomId, long timeout) {
		try {
			TLog.INSTANCE.info(randomId);
			if(isUsed())
				return true;
			ThreadManager.getInstance().addTask(() -> {
				long start = System.currentTimeMillis();
				while (!isUsed()) {
					if(System.currentTimeMillis() >=  start + timeout) {
						setUsed(true);
						queue.add(false);
						break;
					}
				}
			});
			return queue.take();
		} catch(InterruptedException e) {
			return false;
		}
	}

	public void push() {
		queue.add(true);
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public String getCmd() {
		return cmd;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean hasBody() {
		return (packet != null && packet.getBody() != null);
	}
}
