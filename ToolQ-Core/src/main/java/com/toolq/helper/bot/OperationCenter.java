package com.toolq.helper.bot;

import com.google.gson.JsonObject;
import com.toolq.qq.dataClass.*;
import com.toolq.qq.protocol.pay.HbType;

import java.util.List;

public interface OperationCenter {

	/**
	 * 批量获取群资料（机器人必须在该群内）
	 *
	 * @param groupId 群号
	 * @return 群资料
	 */
	List<MultiTroopInfo> getMultiTroopInfo(long... groupId);

	/**
	 * 搜索好友
	 *
	 * @param uin 目标
	 * @return 目标信息
	 */
	UserInfo searchFriend(long uin);


	/**
	 * 获取群列表
	 */
	List<GroupInfo> getTroopList();

	/**
	 * 获取好友列表
	 */
	List<FriendInfo> getFriendList();

	/**
	 * 获取群聊群友信息
	 *
	 * @param groupCode 群号
	 * @param uin 对方QQ
	 */
	GroupMemberInfo getTroopMemberCardInfo(long groupCode, long uin);

	/**
	 * 获取某群聊的分享链接
	 *
	 * @param groupId 群号
	 */
	String getTroopShareUrl(long groupId);

	/**
	 * 点赞
	 *
	 * @param uin 点赞目标
	 * @param time 点赞次数
	 */
	boolean giveFavorite(long uin, int time);

	/**
	 * 获取目标的加好友设置
	 *
	 * @param uin 目标QQ
	 */
	UserFriendInfo getAddFriendSetting(long uin);

	// TODO("好友添加的JavaDoc")
	void addFriend(long uin, FriendAdder friendAdder);

	/**
	 * 设置群聊管理员
	 *
	 * @param troopId 群号
	 * @param uin 目标QQ
	 * @param  isAdd 是否添加
	 */
	boolean setTroopAdmin(long troopId, long uin, boolean isAdd);

	/**
	 * 修改群名片
	 *
	 * @param troop 群号
	 * @param uin 目标QQ
	 * @param  card 群名片
	 */
	boolean modifyTroopCard(long troop, long uin, String card);

	/**
	 * 获取群成员列表
	 *
	 * @param troop 群号
	 */
	List<TroopMemberInfo> getTroopMembers(long troop);

	/**
	 * 群禁言
	 *
	 * @param groupId 群号
	 * @param uin 目标
	 * @param time 时间（秒）
	 */
	boolean troopShutUp(long groupId, long uin, long time);

	/**
	 * 全员禁言
	 *
	 * @param groupId 群号
	 * @param isOpen 是否禁言
	 */
	boolean allMembersMute(long groupId, boolean isOpen);

	/**
	 * 获取群公告列表
	 *
	 * @param groupId 群号
	 */
	JsonObject getGroupAnnouncementList(long groupId);

	/**
	 * 添加群公告
	 *
	 * @param troopId 群号
	 * @param text 内容
	 * @param isTop 置顶
	 * @param isTipWindows 是否弹窗展示
	 * @param isConfirm 是否需要确认
	 * @param showEdit 是否引导修改群名片
	 * @param sendToNewMember 是否发送给新成员
	 */
	JsonObject addTroopNotice(long troopId, String text, boolean isTop, boolean isTipWindows, boolean isConfirm, boolean showEdit, boolean sendToNewMember);

	/**
	 * 删除群公告
	 *
	 * @param troopId 群号
	 * @param fid 群公告id
	 */
	JsonObject delTroopNotice(long troopId, String fid);

	/**
	 * 获取机器人头像
	 */
	String getBotFaceUrl();

	/**
	 * 获取机器人名字
	 */
	String getBotName();

	/**
	 * 获取群成员信息
	 *
	 * @param groupId 来源群号
	 * @param uin QQ
	 */
	TroopUserAppointInfo getTroopAppointRemark(long groupId, long uin);

	/**
	 * 主动加群
	 *
	 * @param troopId 群号
	 * @param backMsg 验证消息
	 */
	boolean joinTroop(long troopId, String backMsg);

	/**
	 * 发红包
	 *
	 * @param troopId 群号
	 * @param type 红包类型
	 * @param tittle 标题
	 * @param amount 总金额（单位：分）
	 * @param number 红包个数
	 * @param exclusive 专属红包目标
	 */
	String troopRedEnvelopes(long troopId, HbType type, String tittle, int amount, int number, Long[] exclusive);
}
