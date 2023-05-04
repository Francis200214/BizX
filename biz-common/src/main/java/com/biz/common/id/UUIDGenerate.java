package com.biz.common.id;

import java.util.UUID;

/**
 * UUID 生成
 *
 * @author francis
 */
public final class UUIDGenerate {

    public static String generate() {
        return UUID.randomUUID().toString();
    }


}
