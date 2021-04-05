package com.qq.taf;

import com.qq.taf.jce.JceStruct;
import com.toolq.helper.jce.JceHelper;

/**
 * @author luoluo
 * @date 2020/10/30 20:23
 */
public class Jce<T> extends JceStruct {
    public T getSelf() {
        return (T) this;
    }
}