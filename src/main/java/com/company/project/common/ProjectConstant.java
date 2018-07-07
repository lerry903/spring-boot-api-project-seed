package com.company.project.common;

/**
 * 项目常量
 * @author lerry
 */
public final class ProjectConstant {

    private ProjectConstant(){

    }

    /**
     * 生成代码所在的基础包名称，可根据自己公司的项目修改
     * 注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类
     */
    public static final String BASE_PACKAGE = "com.company.project";

    public static final String PROD = "prod";

    /**
     * SqlSessionFactoryBean 的model扫描包
     */
    public static final String TYPE_ALIASES_PACKAGE = BASE_PACKAGE+".**.model";

    /**
     * 通用Mapper 的mapper扫描包
     */
    public static final String MAPPER_BASE_PACKAGE = BASE_PACKAGE+".**.dao";

}
