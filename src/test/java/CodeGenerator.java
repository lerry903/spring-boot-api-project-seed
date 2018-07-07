import com.google.common.base.CaseFormat;
import com.company.project.common.mapper.CrudMapper;
import com.company.project.common.result.ResponseResult;
import com.company.project.common.service.AbstractService;
import com.company.project.common.service.Service;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

import static com.company.project.common.ProjectConstant.BASE_PACKAGE;


/**
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 */
public class CodeGenerator {
    //JDBC配置，请修改为你项目的实际配置
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/test/resources/generator/template";//模板位置

    private static final String JAVA_PATH = "/src/main/java"; //java文件路径
    private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径

    private static String subPackage = null; //分模块开发的子包
    private static String basePackage = BASE_PACKAGE + "."+subPackage;
    private static String MAPPER_PACKAGE = basePackage + ".dao";//生成的Mapper所在包
    private static String MODEL_PACKAGE = basePackage + ".model";//生成的Model所在包
    private static String SERVICE_PACKAGE = basePackage + ".service";//生成的Service所在包
    private static String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//生成的ServiceImpl所在包
    private static String CONTROLLER_PACKAGE = basePackage + ".web";//生成的Controller所在包
    private static String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);//生成的Service存放路径
    private static String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);//生成的Service实现存放路径
    private static String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径

    private static final String CREATE_BY = "CodeGenerator";//@createBy
    private static final String AUTHOR = "LErry.li";//@author
    private static final String DATE = DateFormat.getDateInstance().format(new Date());//@date
    private static final String TIME = DateFormat.getTimeInstance().format(new Date());//@time

    /**
     * 是否启用MVC代码生成
     */
    private static final boolean ENABLE_MVC_CODE_GENERATOR = true;
    /**
     * 是否继承Serializable接口
     */
    private static final boolean IMPLEMENTS_SERIALIZABLE = true;

    /**
     * 默认生成的Model名称,需要去掉的表名前缀,不区分大小写
     */
    private static final String REDUCE_TABLE_PREFIX = "T_";

    /**
     * 生成接口的类型
     *  普通POST和RESTful
     */
    private static final String API_TYPE = "RESTful";

    public static void main(String[] args) {
        if(subPackage == null){
            System.out.println("请输入生成代码的子包：");
            try {
                Scanner scan = new Scanner(System.in);
                subPackage = scan.nextLine();
                setPackage(subPackage);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        genCode("t_sys_group", "t_sys_role", "t_sys_user", "t_sys_menu", "t_sys_user_role", "t_sys_role_menu");
        //genCodeByCustomModelName("输入表名","输入自定义Model名称");
    }

    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。
     * 如输入表名称 "t_user_detail" 将生成 TUserDetail、TUserDetailMapper、TUserDetailService ...
     * @param tableNames 数据表名称...
     */
    private static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            genCodeByCustomModelName(tableName, null);
        }
    }

    /**
     * 通过数据表名称，和自定义的 Model 名称生成代码
     * 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User" 将生成 User、UserMapper、UserService ...
     * @param tableName 数据表名称
     * @param modelName 自定义的 Model 名称
     */
    private static void genCodeByCustomModelName(String tableName, String modelName) {
        //如果自定义的model名称为空，且需要去除表名的前缀，则去前缀后的驼峰命名为自定义的model名称
        if(StringUtils.isEmpty(modelName) && StringUtils.isNotEmpty(REDUCE_TABLE_PREFIX)){
            modelName = getUpperCamel(tableName.replaceAll(String.format("^((?i)%s)", REDUCE_TABLE_PREFIX), ""));
        }
        genModelAndMapper(tableName, modelName);
        if(ENABLE_MVC_CODE_GENERATOR){
            genService(tableName, modelName);
            genController(tableName, modelName);
        }
    }


    /**
     * 生成实体类和Mapper
     * @param tableName
     * @param modelName
     */
    private static void genModelAndMapper(String tableName, String modelName) {
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        //添加各种插件
        addPlugin(context);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(MODEL_PACKAGE);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        if (StringUtils.isNotEmpty(modelName)){
            tableConfiguration.setDomainObjectName(modelName);
        }
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            DefaultShellCallback callback = new DefaultShellCallback(true);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        if (StringUtils.isEmpty(modelName)){
            modelName = tableNameConvertUpperCamel(tableName);
        }
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }

    /**
     * 生成service 代码
     * @param tableName
     * @param modelName
     */
    private static void genService(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            setFileHeader(data);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", basePackage);
            data.put("abstractServicePackage", AbstractService.class.getName());
            data.put("servicePackage", Service.class.getName());

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data,
                    new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

            File file1 = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data,
                    new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    /**
     * 生成Controller代码
     * @param tableName
     * @param modelName
     */
    private static void genController(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            setFileHeader(data);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", basePackage);
            data.put("responseResultPackage", ResponseResult.class.getName());

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            String templateName = "controller.ftl";
            if(!"POST".equals(API_TYPE)){
                templateName = "controller-restful.ftl";
            }
            cfg.getTemplate(templateName).process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }

    /**
     * 设置自动生成代码的文件头
     * @param data
     */
    private static void setFileHeader(Map<String, Object> data){
        data.put("date", DATE);
        data.put("author", AUTHOR);
        data.put("time", TIME);
        data.put("createBy", CREATE_BY);
    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

    }

    private static String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();//兼容使用大写的表名
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    private static String modelNameConvertMappingPath(String modelName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
        return tableNameConvertMappingPath(tableName);
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    /**
     * 获取字符串的大骆驼峰形式
     */
    private static String getUpperCamel(String str) {
        //如果全大写，且包含下划线
        if (str.replaceAll("[A-Z]+", "").equals(str) && str.contains("_"))
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
        //如果不包含下划线
        if (!str.contains("_") && str.length() > 2)
            return str.toUpperCase().charAt(0) + str.substring(1);
        //如果不全为大写，且包含下划线
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str.toLowerCase());
    }

    /**
     * 添加各种插件
     * @param context
     */
    private static void addPlugin(Context context){
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        //通用Mapper插件
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", CrudMapper.class.getName());
        context.addPluginConfiguration(pluginConfiguration);
        //实现序列化接口插件
        if(IMPLEMENTS_SERIALIZABLE){
            pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
            context.addPluginConfiguration(pluginConfiguration);
        }
        //生成bean的toString插件
        pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.ToStringPlugin");
        context.addPluginConfiguration(pluginConfiguration);
    }

    /**
     * 如果是在控制台输入子包，这要重新刷新一下个个包的路径
     * @param subPackage 子包路径
     */
    private static void setPackage(String subPackage) {
        basePackage = BASE_PACKAGE + "."+subPackage;
        MAPPER_PACKAGE = basePackage + ".dao";
        MODEL_PACKAGE = basePackage + ".model";
        SERVICE_PACKAGE = basePackage + ".service";
        SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";
        CONTROLLER_PACKAGE = basePackage + ".web";
        PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);//生成的Service存放路径
        PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);//生成的Service实现存放路径
        PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径
    }

}
