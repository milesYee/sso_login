package ${package.Service};

import cn.hutool.core.lang.Dict;
import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhf.core.config.ResultInfo;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {


    /**
     * 初始化接口
     *
     * @return
    */
    Dict init();

    /**
     * 保存${table.comment!}实体类
     *
     * @param ${entity?uncap_first}
     * @return
     */
    ResultInfo saveBean(${entity} ${entity?uncap_first});

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
    */
    ResultInfo<Page<${entity}>> listPage(Long pageNum, Long pageSize);
}
</#if>
