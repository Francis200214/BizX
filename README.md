<h1 align="center">BizX</h1>
<p align="center">
  <img src="https://img.shields.io/badge/Java-8+-blue" alt="Java"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-2.7.x-brightgreen" alt="Spring Boot"/>
  <br/>
  <img src="https://img.shields.io/badge/Author-francis-orange" alt="Author" />
</p>
<hr>


## 简介

BizX 是一个灵活且高效的业务开发框架, 主要提供业务开发中的一些工具类和中间件封装, 让我们在业务开发中只专注于业务。


-------------------------------------------------------------------------------

## 模块介绍

| 模块           | 介绍                                                  |
|--------------|-----------------------------------------------------|
| biz-common   | 工具类模块，其中提供了线程池、JTS、反射、封装策略模式、字符串、日期等常用工具类与封装的一些设计模式 |
| biz-library  | 公共注解模块，主要有对全局出来的Bean注入注解，Web层检查注解等                  |
| biz-config   | 配置模块，主要封装了很多配置，但现在还没做到这里                            |
| biz-core     | 核心模块，封装了Spring的配置、开始BizX框架注解等核心代码                   |
| biz-web      | Web模块，主要包括对Token、检查接口参数等Web层面的业务封装                  |
| biz-cache    | 主要对Caffeine和Redis缓存进行了封装                            |
| biz-oss      | 主要对AmazonS3协议进行了封装, 市面上主流的OSS存储都是遵循了AmazonS3协议      |
| biz-rabbitmq | 主要对RabbitMQ进行了封装                                    |
| biz-redis    | 主要封装了Redis的配置和工具类，实现了开箱即用                           |

引入biz-all可把所有的jar包都引入

# biz-common 模块内部介绍
- Bean 操作：biz-common中对Bean操作进行了封装，可以使用 BizBeanUtils 工具类进行操作 Bean 对象，避免了项目中需要实现 Spring 中的接口才能使用 Bean 的问题。
  BizBeanUtils 中主要提供了一些常用的 Bean 操作方法，如：
  - 获取单个Bean 对象 BizBeanUtils.getBean(class)
  - 获取所有的 Bean 对象集合 BizBeanUtils.getBeanDefinitionClasses()
  - 获取 Bean 对象上带某个直接的 Bean 对象集合 BizBeanUtils.getBeansWithAnnotation(annotation)
- 条形码工具类：对条形码的创建进行了封装，使用 BizBarCodeUtils 工具类进行操作。
  - 主要方法 BarCodeUtils.generate(text, path)，text 为要生成条形码的文本，path 为要生成的条形码图片路径。
- 中文转拼音工具类：对中文转拼音进行了封装，使用 ChineseCharactersUtils 工具类进行操作。主要方法：
  - ChineseCharactersUtils.getPinyin(chinese)，中文转成拼音全拼，chinese 参数为要转换的中文。
  - ChineseCharactersUtils.getPinyinFirst(chinese)，中文转成拼音后取拼音的第一个首字母，chinese 参数为要转换的中文。
- ExecutorsUtils 线程池工具类：对使用的线程池进行了封装，在不指定线程池参数的情况下，根据本机器的配置自动调整线程池大小，保证线程池的效率。
- BizScheduledFuture：当业务中使用到定时任务时，可以使用 BizScheduledFuture 工具类进行操作。其中提供了提交、取消和重置任务的能力，并封装了ScheduledExecutorService的使用，同时也对并发的情况做了处理。
- 



### Bean 操作
biz-common中对Bean操作进行了封装，可以使用 BizBeanUtils 工具类进行操作 Bean 对象，避免了项目中需要实现 Spring 中的接口才能使用 Bean 的问题。



## 鸣谢

- 感谢 JetBrains 提供的免费开源 License：

<p>
<img src="https://images.gitee.com/uploads/images/2020/0406/220236_f5275c90_5531506.png" alt="图片引用自lets-mica" style="float:left;">
</p>