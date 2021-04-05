package com.toolq.qq.dataClass

data class QPayWallet(
    val ad_display: String,
    val authen_account_type: String,
    val authen_channel_state: String,
    val authen_channel_state_hex: String,
    val balance: String,
    val bind_ad_flag: String,
    val bind_ad_title: String,
    val bind_ad_url: String,
    val bind_banks: List<Any>,
    val charge_limit: String,
    val creditcard_id: String,
    val creditcard_type: String,
    val domain_name: String,
    val if_open_charge: String,
    val interf_cache_ver: String,
    val is_register: String,
    val pass_flag: String,
    val purchaser_id: String,
    val purchaser_true_name: String,
    val retcode: String,
    val retmsg: String,
    val skey: String,
    val skey_expire: String,
    val time_stamp: String,
    val time_window: String,
    val trans_seq: String,
    val user_attr: String,
    val user_info_extend: UserInfoExtend,
    val user_type: String
)

data class UserInfoExtend(
    val creditcard_exp_date: String,
    val need_fill: String,
    val need_fill_new: String,
    val realauth_content: String,
    val realauth_url: String
)
