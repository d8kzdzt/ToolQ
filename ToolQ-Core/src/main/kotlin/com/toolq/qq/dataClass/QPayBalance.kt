package com.toolq.qq.dataClass

data class QPayBalance(
    val bargainor_id: String,
    val callback_url: String,
    val pay_flag: String,
    val pay_time: String,
    val real_fee: String,
    val retcode: String,
    val retmsg: String,
    val send_flag: String,
    val sp_billno: String,
    val sp_data: String,
    val transaction_id: String
)