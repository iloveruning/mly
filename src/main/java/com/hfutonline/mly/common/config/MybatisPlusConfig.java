package com.hfutonline.mly.common.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenliangliang
 * @date 2018/1/26
 */
@Configuration
@MapperScan(basePackages = "com.hfutonline.mly.modules.*.mapper")
public class MybatisPlusConfig {



    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        paginationInterceptor.setDialectClazz("com.baomidou.mybatisplus.plugins.pagination.dialects.MySqlDialect");
        //paginationInterceptor.setLocalPage(true);// 开启 PageHelper 的支持
        return paginationInterceptor;
    }


//    /**
//     * mybatis的默认配置
//     */
//    @Bean
//    public MybatisConfiguration mybatisConfiguration() {
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        //该配置影响的所有映射器中配置的缓存的全局开关。
//        configuration.setCacheEnabled(true);
//        //当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载
//        configuration.setAggressiveLazyLoading(false);
//
//        /**
//         * 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。
//         * 特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态。默认：false
//         */
//        configuration.setLazyLoadingEnabled(true);
//
//        /**
//         * 是否允许单一语句返回多结果集（需要兼容驱动）。默认：true
//         */
//        //configuration.setMultipleResultSetsEnabled(true);
//
//        /**
//         * 使用列标签代替列名。不同的驱动在这方面会有不同的表现，
//         * 默认：true
//         */
//        //configuration.setUseColumnLabel(true);
//
//        /**
//         * 允许 JDBC 支持自动生成主键，需要驱动兼容。
//         * 如果设置为 true 则这个设置强制使用自动生成主键，尽管一些驱动不能兼容但仍可正常工作（比如 Derby）。
//         * 默认：false
//         */
//        //configuration.setUseGeneratedKeys(true);
//
//        /**
//         * 指定 MyBatis 应如何自动映射列到字段或属性。
//         * NONE 表示取消自动映射；
//         * PARTIAL 只会自动映射没有定义嵌套结果集映射的结果集。(默认)
//         * FULL 会自动映射任意复杂的结果集（无论是否嵌套）。
//         */
//        //configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
//
//        /**
//         * 指定发现自动映射目标未知列（或者未知属性类型）的行为。
//         NONE: 不做任何反应  (默认)
//         WARNING: 输出提醒日志 ('org.apache.ibatis.session.AutoMappingUnknownColumnBehavior' 的日志等级必须设置为 WARN)
//         FAILING: 映射失败 (抛出 SqlSessionException)
//         */
//        //configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.WARNING);
//
//        /**
//         * 配置默认的执行器。
//         * SIMPLE 就是普通的执行器； (默认)
//         * REUSE 执行器会重用预处理语句（prepared statements）；
//         * BATCH 执行器将重用语句并执行批量更新。
//         */
//        configuration.setDefaultExecutorType(ExecutorType.REUSE);
//
//        /**
//         * 设置超时时间，它决定驱动等待数据库响应的秒数。
//         */
//        //configuration.setDefaultStatementTimeout(2500);
//
//        /**
//         * 指定动态 SQL 生成的默认语言。
//         */
//        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
//
//        /**
//         * 当返回行的所有列都是空时，MyBatis默认返回null。
//         * 当开启这个设置时，MyBatis会返回一个空实例。
//         * 请注意，它也适用于嵌套的结果集 (i.e. collectioin and association)。（从3.4.2开始）
//         */
//        configuration.setReturnInstanceForEmptyRow(true);
//        //部分数据库不识别默认的NULL类型（比如oracle，需要配置该属性
//        //configuration.setJdbcTypeForNull(JdbcType.NULL);
//
//        /**
//         * 是否开启自动驼峰命名规则（camel case）映射，默认：false
//         * 即从经典数据库列名 A_COLUMN 到经典 Java 属性名 aColumn 的类似映射。
//         */
//        configuration.setMapUnderscoreToCamelCase(true);
//        return mybatisConfiguration();
//    }
//
//
//    @Bean("mybatisSqlSession")
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisConfiguration mybatisConfiguration, GlobalConfiguration globalConfiguration) throws Exception {
//        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(dataSource);
//
//        //配置实体扫描路径，多个package可以用分号; 逗号, 分隔， 支持通配符*
//        sqlSessionFactory.setTypeAliasesPackage("com.cll.mybatisPlus.entity");
//
//        //
//        sqlSessionFactory.setMapperLocations(new Resource[]{
//                new ClassPathResource("/mapper/*Mapper.xml")
//        });
//        //mybatis的默认配置
//        sqlSessionFactory.setConfiguration(mybatisConfiguration);
//
//        // 分页插件配置
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        paginationInterceptor.setDialectType("mysql");
//        paginationInterceptor.setDialectClazz("com.baomidou.mybatisplus.plugins.pagination.dialects.MySqlDialect");
//        //paginationInterceptor.setLocalPage(true);// 开启 PageHelper 的支持
//
//        sqlSessionFactory.setPlugins(new Interceptor[]{
//
//                paginationInterceptor,
//                //乐观锁插件
//                // new PerformanceInterceptor(),
//                //性能拦截器，兼打印sql，不建议生产环境配置
//                new OptimisticLockerInterceptor()
//        });
//        sqlSessionFactory.setGlobalConfig(globalConfiguration);
//        return sqlSessionFactory.getObject();
//    }
//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer(){
//        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
//        mapperScannerConfigurer.setBasePackage("com.cll.mybatisPlus.mapper");
//        return mapperScannerConfigurer;
//    }
//
//    @Bean
//    public GlobalConfiguration globalConfiguration() {
//        GlobalConfiguration conf = new GlobalConfiguration();
//
//        /**
//         * 主键类型
//         * 0:"数据库ID自增",
//         * 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)",
//         * 3:"全局唯一ID UUID";
//         */
//        conf.setIdType(2);
//
//        /**
//         * 字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
//         */
//        conf.setFieldStrategy(1);
//
//        /**
//         * 驼峰下划线转换
//         */
//        conf.setDbColumnUnderline(true);
//
//        /**
//         * 刷新mapper 调试神器
//         */
//        //conf.setRefresh(true);
//
//
//        /**
//         * 逻辑删除配置（下面3个配置）
//         */
//        //conf.setLogicDeleteValue("-1");
//        //conf.setLogicNotDeleteValue("1");
//        //conf.setSqlInjector(new LogicSqlInjector());
//
//        //自定义填充策略接口实现
//        //conf.setMetaObjectHandler(new H2MetaObjectHandler());
//        return conf;
//    }
}
