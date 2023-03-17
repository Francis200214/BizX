package com.biz.common.transactional;

import java.util.function.Supplier;

/**
 * 事务管理
 *
 * @author francis
 */
public interface TransactionalRunner {

    <TR> TR apply(Supplier<TR> supplier);

    void run(Runnable runnable);

}
