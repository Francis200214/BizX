package com.biz.common.strategy;


/**
 * 选择策略接口
 * <p>
 * 该接口定义了在给定参数的情况下，选择合适策略的操作。它使用泛型来允许灵活的选择和执行特定的策略服务。
 * </p>
 *
 * <p><strong>Parameter_Type:</strong> 表示选择策略时所需的参数类型，增强了代码的通用性和类型安全。</p>
 * <p><strong>T extends StrategyService&lt;Parameter_Type&gt;:</strong> 表示选择的策略必须是StrategyService接口的实现，且该实现适用于特定的参数类型Parameter_Type，这种泛型约束确保了策略的选择和应用是类型匹配的。</p>
 *
 * <p>通过实现该接口，可以在运行时根据不同的参数动态选择适当的策略服务，从而使应用程序更加灵活和可扩展。</p>
 *
 * <pre>{@code
 * // 示例用法
 * // 示例类：支付类型
 * public enum PaymentType {
 *     // 信用卡支付
 *     CREDIT_CARD,
 *     // PayPal支付
 *     PAY_PAL
 * }
 *
 * // 示例类：支付信息
 * public class PaymentDetails {
 *     private final PaymentType type;
 *     private final String name;
 *
 *     public PaymentDetails(PaymentType type, String name) {
 *         this.type = type;
 *         this.name = name;
 *     }
 *
 *     public PaymentType getType() {
 *         return type;
 *     }
 *
 *     public String getName() {
 *         return name;
 *     }
 * }
 *
 * // 策略接口定义
 * public interface PaymentStrategy extends StrategyService<PaymentType> {
 *     void pay(PaymentDetails details);
 * }
 *
 * // 具体策略实现：信用卡支付
 * public class CreditCardPaymentStrategy implements PaymentStrategy {
 *     @Override
 *     public boolean check(PaymentType type) {
 *         return type == PaymentType.CREDIT_CARD;
 *     }
 *
 *     @Override
 *     public void pay(PaymentDetails details) {
 *         // 实现信用卡支付逻辑
 *         System.out.println("Processing credit card payment");
 *     }
 * }
 *
 * // 具体策略实现：PayPal支付
 * public class PayPalPaymentStrategy implements PaymentStrategy {
 *     @Override
 *     public boolean check(PaymentType type) {
 *         return type == PaymentType.PAY_PAL;
 *     }
 *
 *     @Override
 *     public void pay(PaymentDetails details) {
 *         // 实现PayPal支付逻辑
 *         System.out.println("Processing PayPal payment");
 *     }
 * }
 *
 * // 使用策略选择器
 * public class PaymentProcessor {
 *     private final SelectableStrategyService<PaymentType, PaymentStrategy> selector = new LazySelectableAbstractStrategyService<PaymentType, PaymentStrategy>() {};
 *
 *     public void processPayment(PaymentDetails details) {
 *         PaymentStrategy strategy = selector.select(details.getType());
 *         if (strategy != null) {
 *             strategy.pay(details);
 *         } else {
 *             System.out.println("Payment details do not match the selected strategy.");
 *         }
 *     }
 * }
 *
 * }</pre>
 *
 * @param <Parameter_Type> 选择策略时所需的参数类型。
 * @param <T>              策略服务的具体类型，必须实现StrategyService接口并适用于给定的参数类型。
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
public interface SelectableStrategyService<Parameter_Type, T extends StrategyService<Parameter_Type>> {

    /**
     * 根据参数选择策略
     * <p>
     * 该方法接受一个特定类型的参数，根据该参数的值或特性选择并返回一个合适的策略服务。
     * 这种动态选择策略的能力是策略模式的核心，它允许在运行时根据具体情况灵活地切换算法或行为。
     * </p>
     *
     * @param param 用于选择策略的参数，其类型应与Parameter_Type一致。
     * @return 返回一个策略服务实例，该实例是StrategyService接口的特定实现，适用于处理param类型的参数。
     */
    T select(Parameter_Type param);
}
