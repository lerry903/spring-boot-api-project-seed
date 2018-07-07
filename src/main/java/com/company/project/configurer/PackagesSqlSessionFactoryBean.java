package com.company.project.configurer;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 为了解决typeAliasesPackage 默认只能扫描某一个路径，不支持通配符和正则。
 * 所以重写SqlSessionFactoryBean的setTypeAliasesPackage方法
 *
 * @author LErry.li
 * Date: 2018/7/7
 * Time: 下午5:05
 */
public class PackagesSqlSessionFactoryBean extends SqlSessionFactoryBean {

    private final Logger logger = LoggerFactory.getLogger(PackagesSqlSessionFactoryBean.class);


    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    @Override
    public void setTypeAliasesPackage(String typeAliasesPackage) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        typeAliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(typeAliasesPackage) + "/" + DEFAULT_RESOURCE_PATTERN;

        //将加载多个绝对匹配的所有Resource
        //将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分
        //然后进行遍历模式匹配
        try {
            List<String> result = new ArrayList<>();
            Resource[] resources = resolver.getResources(typeAliasesPackage);
            if (resources.length > 0) {
                MetadataReader metadataReader = null;
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
                    }
                }
            }
            if (!result.isEmpty()) {
                super.setTypeAliasesPackage(StringUtils.join(result.toArray(), ","));
            } else {
                String msg = String.format("参数typeAliasesPackage:%s，未找到任何包", typeAliasesPackage);
                logger.warn(msg);
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.info(e.getMessage());
        }
    }

}
