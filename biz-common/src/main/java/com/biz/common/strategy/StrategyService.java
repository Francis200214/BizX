package com.biz.common.strategy;

/**
 * 策略模式父接口
 * <p>
 * 该接口定义了策略模式中的行为，即对给定参数进行检查的操作。
 * 具体的检查逻辑由实现该接口的类来完成。
 * 使用策略模式可以灵活地根据不同的条件或场景选择不同的检查策略。
 *
 * @param <Parameter> 检查操作所接受的参数类型
 * @author francis
 */
public interface StrategyService<Parameter> {

    /**
     * 对给定参数进行检查。
     * <p>
     * 该方法由具体的策略类实现，实现类中应定义具体的检查逻辑。
     * 检查结果为true表示参数通过检查，为false表示参数未通过检查。
     *
     * @param parameter 要进行检查的参数
     * @return boolean 检查结果，true表示参数有效，false表示参数无效
     */
    boolean check(Parameter parameter);

}
