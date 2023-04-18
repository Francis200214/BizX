package com.biz.common.utils;

import java.util.UUID;

/**
 * UUID 生成
 *
 * @author francis
 */
public final class UUIDGenerate {

    public String generate() {
        return UUID.randomUUID().toString();
    }


}
