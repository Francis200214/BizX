package com.biz.common.poi;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author: francis
 * @description: excel 工具类
 * @create: 2023-04-03 22:26
 **/
public final class ExcelUtils {


//    /**
//     * 读取Excel文件
//     *
//     * @param path Excel文件里路径
//     * @param model 读取后的实体类
//     * @param function 每次读取的操作
//     * @param overSupplier 全部读取后的操作
//     * @param <T>
//     */
//    public static <T> void read(String path, Class<T> model, Function function, Supplier overSupplier) {
//        EasyExcel.read(path, model, new AnalysisEventListener<T>() {
//            // 每读取一行数据, 该方法会被调用一次
//            @Override
//            public void invoke(T t, AnalysisContext analysisContext) {
//                supplier.get();
//            }
//
//            // 全部读取完成被调用
//            @Override
//            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//                overSupplier.get();
//            }
//        }).sheet().doRead();
//    }

}
