package com.biz.common.strategy;

/**
 * 策略模式父接口。
 * <p>
 * 该接口定义了策略模式中的行为，即对给定参数进行检查的操作。
 * 具体的检查逻辑由实现该接口的类来完成。
 * 使用策略模式可以灵活地根据不同的条件或场景选择不同的检查策略，从而提高代码的可维护性和扩展性。
 * </p>
 *
 * <p>通过实现该接口，可以为不同的检查需求定义具体的策略类。例如，验证输入参数的合法性、
 * 检查业务逻辑的条件等。</p>
 *
 * <pre>{@code
 * // 示例用法
 * public class AgeCheckStrategy implements StrategyService<Integer> {
 *     @Override
 *     public boolean check(Integer age) {
 *         return age >= 18;
 *     }
 * }
 *
 * // 创建策略实例
 * StrategyService<Integer> ageCheck = new AgeCheckStrategy();
 *
 * // 使用策略进行检查
 * boolean isAdult = ageCheck.check(20); // 返回true
 *
 * // 另一个示例：实现一个检查字符串是否为空的策略
 * public class NonEmptyStringStrategy implements StrategyService<String> {
 *     @Override
 *     public boolean check(String input) {
 *         return input != null && !input.isEmpty();
 *     }
 * }
 *
 * // 创建并使用字符串检查策略
 * StrategyService<String> nonEmptyCheck = new NonEmptyStringStrategy();
 * boolean isValid = nonEmptyCheck.check("Hello, World!"); // 返回true
 * boolean isInvalid = nonEmptyCheck.check(""); // 返回false
 * }</pre>
 *
 * @param <Parameter> 检查操作所接受的参数类型
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
public interface StrategyService<Parameter> {

    /**
     * 对给定参数进行检查。
     * <p>
     * 该方法由具体的策略类实现，实现类中应定义具体的检查逻辑。
     * 检查结果为true表示参数通过检查，为false表示参数未通过检查。
     * </p>
     *
     * @param parameter 要进行检查的参数
     * @return boolean 检查结果，true表示参数有效，false表示参数无效
     */
    boolean check(Parameter parameter);

}
