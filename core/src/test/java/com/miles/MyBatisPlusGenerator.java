package com.miles;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

/**
 * MyBatisPlus代码生成器
 * 3.5.6
 */
public class MyBatisPlusGenerator {
    // jdbc路径
    private static String url = "jdbc:mysql://192.168.0.107:3309/file_minio?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true";
    // 数据库账号
    private static String username = "root";
    // 数据库账号
    private static String password = "root";
    //模块名
    private static String moduleName = "finance";
    //功能名
    private static String functionName = "";
    //作者名字
    private static String author = "lcry";
    //entity中的字段,updateStrategy配置,true:更新sql语句中永远会包含此字段,false:默认配置
    private static boolean updateStrategy = true;
    //是否包含权限注解
    private static boolean containsDataAuth = false;
    //权限注解忽略字段,containsDataAuth=true生效
    private static List<String> excludeFields = Arrays.asList("id", "uuid");

    public static void main(String[] args) {
        // 获取当前目录路径
        String projectPath = System.getProperty("user.dir");
        // 代码生成器: 数据源配置
        AutoGenerator autoGenerator = new AutoGenerator(initDataSourceConfig());
        // 全局配置
        autoGenerator.global(initGlobalConfig(projectPath));
        // 包配置，如模块名、实体、mapper、service、controller等
        autoGenerator.packageInfo(initPackageConfig());
        // 模板配置
        autoGenerator.template(initTemplateConfig());
        // 自定义配置
        autoGenerator.injection(initInjectionConfig(projectPath));
        // 策略配置
        autoGenerator.strategy(initStrategyConfig());
        // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        //autoGenerator.execute(new VelocityTemplateEngine());
        autoGenerator.execute(new FreemarkerTemplateEngine());
    }

    /**
     * 读取控制台内容信息
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        if (scanner.hasNext()) {
            String next = scanner.next();
            if (StrUtil.isNotEmpty(next)) {
                return next;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 初始化数据源配置
     */
    private static DataSourceConfig initDataSourceConfig() {
        // 读取当前源文件配置
        return new DataSourceConfig
                .Builder(url, username, password) //设置数据源
                .dbQuery(new MySqlQuery())     //设置数据库查询器
                .build();
    }

    /**
     * 初始化全局配置
     */
    private static GlobalConfig initGlobalConfig(String projectPath) {
        return new GlobalConfig.Builder()
                .outputDir(projectPath + File.separator + moduleName + "/src/main/java")//指定生成文件的根目录
                .author(author)             //Author设置作者
                .disableOpenDir()           //禁止打开输出目录,禁止打开输出目录
                //.enableSwagger()            //开启 swagger 模式,开启 swagger 模式
//                .fileOverride()             //禁止打开输出目录,默认值：false
                .dateType(DateType.ONLY_DATE)//设置时间类型
                .commentDate("yyyy-MM-dd") //注释日期
                .build();
    }

    /**
     * 初始化包配置
     */
    private static PackageConfig initPackageConfig() {
//        Map<OutputFile, String> pathInfo = new HashMap();
        return new PackageConfig
                .Builder()
                .parent(String.format("com.jhf.%s", moduleName.toLowerCase(Locale.ROOT)))  // 父包名
//                .moduleName(moduleName)      //父包模块名
                .entity(StrUtil.isNotEmpty(functionName) ? String.format("bean.entity.%s", functionName.toLowerCase()) : "bean.entity")             //实体类 Entity 包名,默认值：entity
                .service(StrUtil.isNotEmpty(functionName) ? String.format("service.%s", functionName.toLowerCase()) : "service")          //Service 包名,默认值：entity
                .serviceImpl(StrUtil.isNotEmpty(functionName) ? String.format("service.%s.impl", functionName.toLowerCase()) : "service.impl") //实现类 Service Impl 包名	默认值：service.impl
                .mapper(StrUtil.isNotEmpty(functionName) ? String.format("mapper.%s", functionName.toLowerCase()) : "mapper")            //Mapper 包名	默认值：mapper
                .xml(StrUtil.isNotEmpty(functionName) ? String.format("mapper.xml.%s", functionName.toLowerCase()) : "mapper.xml")           //Mapper XML 包名	默认值：mapper.xml
                .controller(StrUtil.isNotEmpty(functionName) ? String.format("controller.%s", functionName.toLowerCase()) : "controller")    //Controller 包名	默认值：controller
//                .other("other")              //自定义文件包名	可使用"other"，生产一个other文件目录
//                .pathInfo(pathInfo)          //路径配置信息
                .build();
    }

    /**
     * 初始化模板配置
     * 可以对controller、service、entity模板进行配置
     */
    private static TemplateConfig initTemplateConfig() {
        return new TemplateConfig.Builder()
                .entity("/templates/entity.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .mapper("/templates/mapper.java")
                .xml("/templates/mapper.xml")
                .controller("/templates/controller.java")
                .build();
    }

    /**
     * 初始化自定义配置
     */
    private static InjectionConfig initInjectionConfig(String projectPath) {
        /**自定义生成模板参数**/
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("containsDataAuth", containsDataAuth);
        paramMap.put("excludeFields", excludeFields);
        paramMap.put("updateStrategy", updateStrategy);
        List<Long> maxValues = new ArrayList<>();
        maxValues.add(1L);
        for (int i = 1; i < 30; i++) {
            maxValues.add(maxValues.get(i-1)*10);
            maxValues.set(i-1,maxValues.get(i-1)-1);
        }
        maxValues.set(maxValues.size()-1,maxValues.get(maxValues.size()-1)-1);
        paramMap.put("maxValues", maxValues);
        /** 自定义 生成类**/
        Map<String, String> customFileMap = new HashMap();
//        customFileMap.put("/api/%sApi.js", "templates/vue/api.js.vm");
//        customFileMap.put("/%s/index.vue", "templates/vue/index.vue.vm");
        // 自定义配置
        return new InjectionConfig.Builder()
                .beforeOutputFile((tableInfo, objectMap) -> {
                    customFileMap.forEach((key, value) -> {
                        System.out.println(key + " : " + value);
                    });
                    System.out.println("准备生成文件: " + tableInfo.getEntityName());
                })
                .customMap(paramMap)
                .customFile(customFileMap)
                .build();
    }

    private static Consumer<InjectionConfig.Builder> getInjectionConfigBuilder() {
        return consumer -> {
        };
    }

    /**
     * 初始化策略配置
     */
    private static StrategyConfig initStrategyConfig() {

        StrategyConfig.Builder builder = new StrategyConfig.Builder();
        builder
                .enableCapitalMode()//开启大写命名,默认值：false
                //.enableSkipView()//开启跳过视图,默认值：false
                //.disableSqlFilter()//禁用sql过滤,默认值：true，语法不能支持使用 sql 过滤表的话，可以考虑关闭此开关
                //.enableSchema()//启用 schema, 默认值：false，多 schema 场景的时候打开
                .addTablePrefix("fm_")
                .entityBuilder()
                .naming(NamingStrategy.underline_to_camel)//数据库表映射到实体的命名策略,默认下划线转驼峰命名:NamingStrategy.underline_to_camel
                .columnNaming(NamingStrategy.underline_to_camel)//数据库表字段映射到实体的命名策略,	默认为 null，未指定按照 naming 执行
                //.superClass(BaseBean.class)
                .enableFileOverride()
                .addSuperEntityColumns("createDate", "modifyDate", "createUserId", "modifyUserId", "createUserName", "modifyUserName")
                //.superClass()//设置父类: BaseEntity.class || com.baomidou.global.BaseEntity
                //.idType(IdType.ASSIGN_ID) //全局主键类型
//                .disableSerialVersionUID() // 禁用生成 serialVersionUID,默认值：true
//                .enableColumnConstant()//开启生成字段常量,默认值：false
                .enableChainModel()//开启链式模型,默认值：false
                .enableLombok()//开启 lombok 模型,默认值：false
                .enableRemoveIsPrefix()//开启 Boolean 类型字段移除 is 前缀,默认值：false
                .enableTableFieldAnnotation()//开启生成实体时生成字段注解,默认值：false
                //.enableActiveRecord()//开启 ActiveRecord 模型,默认值：false
                //.logicDeleteColumnName("deleted")   // 逻辑删除字段名(数据库)
                //.logicDeletePropertyName("deleted") // 逻辑删除属性名(实体)
                //.addTableFills(new Column("create_time", FieldFill.INSERT)) // 自动填充配置  create_time  update_time 两种方式
//                .addTableFills(new Column("equipment_name", FieldFill.INSERT_UPDATE))
                //.versionColumnName("version")   // 开启乐观锁
                .formatFileName("%s")//格式化文件名称

                .controllerBuilder()
                .enableFileOverride()
                //.superClass(BaseController.class)//设置父类: BaseController.class || com.baomidou.global.BaseController
                .enableHyphenStyle() //开启驼峰转连字符,默认值：false
                .enableRestStyle()//开启生成@RestController 控制器,默认值：false
                .formatFileName("%sController")//格式化文件名称

                .serviceBuilder()
                .enableFileOverride()
                //.superServiceClass(IBaseService.class) //设置 service 接口父类: BaseService.class || com.baomidou.global.BaseService
                //.superServiceImplClass(BaseServiceImpl.class)//设置 service 实现类父类 : BaseServiceImpl.class || com.baomidou.global.BaseServiceImpl
                .formatServiceFileName("I%sService")//格式化 service 接口文件名称
                .formatServiceImplFileName("%sServiceImpl")//格式化 service 实现类文件名称


                .mapperBuilder()
                //.superClass()//设置父类: BaseMapper.class || com.baomidou.global.BaseMapper
                .enableFileOverride()
                .enableMapperAnnotation()//开启 @Mapper 注解,默认值:false
                .enableBaseResultMap()  //启用 BaseResultMap 生成,默认值:false
                .enableBaseColumnList() //启用 BaseColumnList,默认值:false
                //.convertXmlFileName()
                .formatMapperFileName("%sMapper")//格式化 mapper 文件名称
                .formatXmlFileName("%sMapper");//格式化 xml 实现类文件名称

        builder.addInclude(scanner("请输入要生成的表,多个用,隔开").split(","));
        return builder.build();
    }
}
