package com.toolq.qq.dataClass

import com.google.gson.JsonObject

data class QPayHbGate(
    val retcode : Int,
    val retmsg : String,
    val pay_scene : String,
    val Pfaflag : String,
    val time_stamp : String,
    val skey_expire : String,
    val trans_seq : String,
    val skey : String,
    val bargainor_id : String,
    val bargainor_true_name : String,
    val desc : String,
    val total_fee : String,
    val purchaser_id : String,
    val purchaser_true_name : String,
    // 余额
    val balance : String,
    val creditcard_id : String,
    val creditcard_type : String,
    val mobile : String,
    val user_type : String,
    val user_attr : String,
    val interf_cache_ver : String,
    val pay_types : String,
    val transaction_id : String,
    val pay_types_seq : String
)
