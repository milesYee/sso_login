package ${package.ServiceImpl};

import cn.hutool.core.lang.Dict;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.jhf.core.config.ResultInfo;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 *${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
@Log4j2
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    /**
     * 初始化接口
     *
     * @return
     */
    @Override
    public Dict init() {
        Dict res = initData().get();
        return res;
    }

    /**
     * 保存${table.comment!}实体类
     *
     * @param ${entity?uncap_first}
     * @return
     */
    @Override
    public ResultInfo saveBean(${entity} ${entity?uncap_first}) {
        saveOrUpdate(${entity?uncap_first});
        return ResultInfo.success("保存成功");
    }

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultInfo<Page<${entity}>> listPage(Long pageNum, Long pageSize){
        LambdaQueryChainWrapper<${entity}> lq = lambdaQuery();
        Page datas = lq.page(new Page(pageNum, pageSize));
        return ResultInfo.success(datas);
    }
}
</#if>
