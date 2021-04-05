package com.toolq.helper.socket

import com.toolq.resource.TString
import java.lang.RuntimeException

/**
 * @author luoluo
 * @date 2020/9/30 22:07
 */
class ConnectException(val throwable: Throwable) : RuntimeException(TString.socket_connect_fail.get(), throwable)