package com.toolq.qq.dataClass

/**
 * @author luoluo
 * @date 2020/10/31 9:51
 */
data class MultiTroopInfo(
		/**
		 * 群名称
		 */
		var groupName: String? = null,

		/**
		 * 群号
		 */
		var groupCode: Long = 0,
		var groupUin: Long = 0,

		/**
		 * 群成员数量
		 */
		var groupMemberSize: Int = 0,

		/**
		 * 加群认证类型
		 */
		var certificationType: Long = 0,

		/**
		 * 群主QQ
		 */
		var groupOwnerUin: Long = 0
)