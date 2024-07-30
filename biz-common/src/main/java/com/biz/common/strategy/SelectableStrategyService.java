package com.biz.common.strategy;

/**
 * 选择策略接口
 * <p>
 * 该接口定义了在给定参数的情况下，选择合适策略的操作。它使用泛型来允许灵活的选择和执行特定的策略服务。
 * Parameter_Type: 表示选择策略时所需的参数类型，增强了代码的通用性和类型安全。
 * T extends StrategyService<Parameter_Type>: 表示选择的策略必须是StrategyService接口的实现，且该实现适用于特定的参数类型Parameter_Type，这种泛型约束确保了策略的选择和应用是类型匹配的。
 *
 * @author francis
 */
public interface SelectableStrategyService<Parameter_Type, T extends StrategyService<Parameter_Type>> {

    /**
     * 根据参数选择策略
     * <p>
     * 该方法接受一个特定类型的参数，根据该参数的值或特性选择并返回一个合适的策略服务。
     * 这种动态选择策略的能力是策略模式的核心，它允许在运行时根据具体情况灵活地切换算法或行为。
     *
     * @param param 用于选择策略的参数，其类型应与Parameter_Type一致。
     * @return 返回一个策略服务实例，该实例是StrategyService接口的特定实现，适用于处理param类型的参数。
     */
    T slelect(Parameter_Type param);

}
