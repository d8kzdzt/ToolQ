package com.toolq.qq.dataClass

data class UserInfo(
        // 目标
        val uin: Long = 0,
        // 昵称
        var nick: String? = "",
        // 等级
        val level: Int = 0,
        // 点赞数量
        val voteCount: Int = 0,
        // 年龄
        val age: Int = 0,
        // 生日
        val birthday: Int = 0,
        // 国家
        val country: String? = "",
        // 省份
        val province: String? = "",
        // 城市
        val city: String? = null,
        // 邮箱
        val email: String? = "",
        // 照片墙图片数量
        val photoCount: Int = 0,
        // 未知
        val userFlag: Long = 0,
        // Face
        val face: Int = 0,
        // 默认备注
        val autoRemark: String? = "",
        // 说说摘要
        val qzoneFeedsDesc: String? = "",
        // 备注
        val remark: String? = "",
        // 展示名
        val showName: String? = ""
)