package com.toolq.helper.bot;

public enum MsgStatus {
    /**
     * 忽略消息
     * 将消息交给下一个消息处理器处理
     * 在并发插件机制下无效
     */
    IGNORE,
    /**
     * 劫持消息
     * 该消息处理器劫持该消息
     * 接下来所有消息处理器将不会处理该消息
     * 在并发消息处理机制下无效
     */
    HIJACK
}
