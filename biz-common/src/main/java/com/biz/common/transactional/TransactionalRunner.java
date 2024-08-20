package com.biz.common.transactional;

import java.util.function.Supplier;

/**
 * 事务管理器接口，提供事务性操作的抽象。
 * 通过此接口，可以在需要执行事务的上下文中运行代码，确保代码的原子性、一致性和持久性。
 * 支持两种典型的操作模式：返回值的函数式编程和无返回值的命令式编程。
 *
 * <p>该接口定义了在事务环境中执行操作的两种方式：一种是没有返回值的操作，
 * 适用于仅需确保操作的事务性而不需要返回结果的场景；另一种是有返回值的操作，
 * 适用于需要在事务环境中执行操作并返回结果的场景。</p>
 *
 * <pre>{@code
 * // 示例用法
 * // 需要实现该接口，并将该实现类注册为Spring的Bean，同时添加@Transactional注解
 * @Component
 * @Transactional(rollbackFor = Throwable.class)
 * public class TransactionalServiceImpl implements TransactionalRunner {
 *
 *     // 出现异常时，事务会回滚，事务管理由@Transactional注解处理
 *     @Override
 *     public <TR> TR apply(Supplier<TR> supplier) {
 *         return supplier.get();
 *     }
 *
 *     // 出现异常时，事务会回滚，事务管理由@Transactional注解处理
 *     @Override
 *     public void run(Runnable runnable) {
 *         runnable.run();
 *     }
 * }
 *
 * // 使用示例
 * @Component
 * public class UserService {
 *
 *     @Autowired
 *     private TransactionalRunner transactionalRunner;
 *
 *     @Autowired
 *     private IUserService iUserService;
 *
 *     @Autowired
 *     private IUserRoleService iUserRoleService;
 *
 *     // 添加用户以及用户角色
 *     public void addUser() {
 *         transactionalRunner.run(() -> {
 *             // 执行需要事务管理的代码
 *             // 添加用户
 *             iUserService.addUser();
 *             // 添加用户角色
 *             iUserRoleService.addUserRole();
 *         });
 *     }
 *
 * }
 * }</pre>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
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
