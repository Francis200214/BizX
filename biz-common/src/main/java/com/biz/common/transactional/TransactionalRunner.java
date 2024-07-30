package com.biz.common.transactional;

import java.util.function.Supplier;

/**
 * 事务管理器接口，提供事务性操作的抽象。
 * 通过此接口，可以在需要执行事务的上下文中运行代码，确保代码的原子性、一致性和持久性。
 * 支持两种典型的操作模式：返回值的函数式编程和无返回值的命令式编程。
 *
 * @author francis
 */
public interface TransactionalRunner {

    /**
     * 在事务上下文中执行无返回值的代码块。
     * 此方法适用于需要执行一系列数据库操作，但不关心具体返回结果的场景。
     * 事务会确保这些操作要么全部成功，要么在发生异常时全部回滚。
     *
     * @param runnable 在事务上下文中执行的代码块。
     */
    void run(Runnable runnable);

    /**
     * 在事务上下文中执行有返回值的代码块。
     * 此方法适用于需要在事务环境下执行操作，并返回操作结果的场景。
     * 事务会确保操作的原子性，即要么全部完成，要么在异常情况下回滚。
     * 返回类型为泛型，允许执行各种类型的操作并返回相应的结果。
     *
     * @param supplier 在事务上下文中执行并返回结果的代码块。
     * @param <TR>     供应商返回的结果类型。
     * @return 代码块执行后的结果。
     */
    <TR> TR apply(Supplier<TR> supplier);

}

