package com.toolq.helper.socket

import com.toolq.resource.TString
import java.lang.RuntimeException

/**
 * @author luoluo
 * @date 2020/10/1 0:27
 */
class SocketClientException(val throwable: Throwable) : RuntimeException(TString.socket_error.get(), throwable)