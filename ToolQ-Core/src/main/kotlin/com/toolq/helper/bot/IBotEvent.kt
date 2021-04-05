package com.toolq.helper.bot

@ExperimentalUnsignedTypes
interface IBotEvent {
    /**
     * 好友消息
     *
     * @param fromNick 对方昵称
     * @param fromUin 对方QQ
     * @param msgTime 消息时间
     * @param msgId 消息ID
     * @param msg 消息内容（TQ码）
     * @param msgInfo 消息额外信息
     * @return MsgStatus
     */
    fun friendEvent(
        fromNick: String,
        fromUin: Long,
        msgTime: Long,
        msgId: Int,
        msg: String,
        msgInfo: MsgInfo
    ): MsgStatus

    /**
     * 群聊消息
     *
     * @param fromGroupName 来源群昵称
     * @param fromGroup 来源群号
     * @param fromGroupLevel 来源群等级
     * @param fromNick 对方昵称
     * @param fromUin 对方QQ
     * @param msgTime 消息时间
     * @param msgId 消息ID
     * @param msg 消息内容（TQ码）
     * @param msgInfo 消息额外信息
     * @return MsgStatus
     */
    fun groupEvent(
        fromGroupName: String,
        fromGroup: Long,
        fromGroupLevel: Int,
        fromNick: String,
        fromUin: Long,
        msgTime: Long,
        msgId: Int,
        msg: String,
        msgInfo: MsgInfo
    ): MsgStatus

    /**
     * 消息处理器添加事件
     *
     * @param botEvent 处理器
     * @return 是否允许添加处理器
     */
    fun handleAddEvent(botEvent: IBotEvent): Boolean = true

    /**
     * 心跳事件
     *
     * @param time 心跳时间(毫秒)
     */
    fun heartbeatEvent(time: Long) {}

    /**
     * 消息已推送事件
     *
     * @param result 返回结果
     * 120 被禁言
     * 110 消息被拒绝
     * 0 发送成功
     * 10 消息体过于庞大
     * 34 消息过长
     * 1 消息包错误
     * 130 匿名消息被禁止
     * 131 匿名用户被禁言
     * 299 受每分钟发言次数限制
     * @param errMsg 错误提示
     */
    fun messageSentEvent(result: Int, errMsg: String) {}

    /**
     * Bot被抢登事件
     *
     * @param tittle 标题
     * @param tip 提示
     * @param sameDevice 是否是同一个设备
     */
    fun dropEvent(tittle : String, tip : String, sameDevice : Boolean)

    /**
     * 群消息撤回事件
     *
     * @param troopId 群号
     * @param uin 撤回操作者
     * @param time 撤回时间
     * @param authorUin 被撤回消息的人
     * @param msgSeq 消息ID
     * @param msgTime 消息发送时间
     * @param tip 撤回提示
     */
    fun troopMsgWithdrawalEvent(troopId : ULong, uin: ULong, time: ULong, authorUin: ULong, msgSeq: UInt, msgTime: ULong, tip: String)

    /**
     * 好友消息撤回事件
     *
     * @param uin 撤回操作者
     * @param time 撤回时间
     * @param msgSeq 消息ID
     * @param msgTime 消息发送时间
     * @param tip 撤回提示
     */
    fun friendMsgWithdrawalEvent(uin: ULong, time: ULong, msgSeq: UInt, msgTime: ULong, tip: String)

    /**
     * 群管理改变事件
     *
     * @param isDelete 是否被删除
     * @param fromGroup 来源群号
     * @param toUin 被操作人
     * @param msgTime 操作时间
     * @param msgId 通知事件id
     */
    fun adminIncreaseAndDecreaseEvent(isDelete : Boolean, fromGroup : Long, toUin : Long, msgTime : Long, msgId : Int, msgUid : Long)

    /**
     * 群名称改变事件
     *
     * @param troopId 群号
     * @param content 内容
     * @param time 变更时间
     * @param sender 操作人
     */
    fun troopNameChanged(troopId : Long, content : String, time : Long, sender : Long)

    /**
     * 群禁言事件
     *
     * @param troopId 群号
     * @param sender 操作人
     * @param time 操作时间
     * @param banUin 被禁言者
     * @param banTime 禁言时间（秒）
     */
    fun groupBanEvent(troopId: Long, sender: Long, time : Long, banUin : Long, banTime : Long)

    /**
     * 群成员添加事件
     *
     * @param troopId 群号
     * @param joinUin 新人
     * @param joinTime 操作时间
     * @param isInvited 是否被邀请
     * @param inviteUin 邀请人
     */
    fun groupMemberJoin(troopId: Long, joinUin : Long, uinNick : String, joinTime : Long, isInvited : Boolean, inviteUin : Long)

    /**
     * 群成员减少事件
     *
     * @param troopId 群号
     * @param leaveUin 离开者
     * @param leaveTime 离开时间
     * @param uin 操作人
     */
    fun groupMemberLeave(troopId: Long, leaveUin : Long, leaveTime : Long, uin : Long)

    /**
     * 群文件上传
     *
     * @param troopId 群号
     * @param fromUin 文件上传人
     * @param msgTime 消息发送时间
     * @param fileName 文件名称
     * @param fileSize 文件大小
     * @param fileDeadTime 文件过期时间
     * @param fileMd5 文件MD5
     * @param filePath 文件路径
     */
    fun groupFileUp(troopId: Long, fromUin: Long, msgTime: Long, fileName : String, fileSize : Long, fileDeadTime : Long, fileMd5 : String, filePath : String)
}

