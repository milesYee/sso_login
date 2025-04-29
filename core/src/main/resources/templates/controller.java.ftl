package ${package.Controller};

import cn.hutool.core.lang.Dict;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jhf.core.config.ResultInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.BindingResult;
import java.util.Arrays;
import lombok.extern.log4j.Log4j2;
import javax.validation.Valid;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
<#if restControllerStyle>
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * ${table.comment!}
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@Log4j2
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName?replace("-","_")}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen?replace("-","_")}<#else>${table.entityPath?replace("-","_")}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} extends ${superControllerClass}{
</#if>
    @Autowired
    private ${table.serviceName} ${table.serviceName?substring(1)?uncap_first};

    /**
    * 初始化接口
    *
    * @return
    */
    @PostMapping("/init")
    public ResultInfo init() {
        Dict result =  ${table.serviceName?substring(1)?uncap_first}.init();
        return ResultInfo.success(result);
    }
    /**
     * 新增/保存
     *
     * 需要新增<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName?replace("-","_")}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen?replace("-","_")}<#else>${table.entityPath?replace("-","_")}</#if>/save权限,否则无法访问
     *
     * @param ${entity?uncap_first}
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName?replace("-","_")}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen?replace("-","_")}<#else>${table.entityPath?replace("-","_")}</#if>/save")
    public ResultInfo save(@Valid @RequestBody ${entity} ${entity?uncap_first}, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return ResultInfo.error(bindingResult.getFieldError().getDefaultMessage());
        }
        return ${table.serviceName?substring(1)?uncap_first}.saveBean(${entity?uncap_first});
    }

    /**
     * 通过主键获取
     *
     * 必须要有<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName?replace("-","_")}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen?replace("-","_")}<#else>${table.entityPath?replace("-","_")}</#if>/get的权限,否则无法访问
     *
     * @param uuid
     * @return
     */
    @PostMapping("/get")
    @RequiresPermissions("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName?replace("-","_")}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen?replace("-","_")}<#else>${table.entityPath?replace("-","_")}</#if>/get")
    public ResultInfo get(String uuid) {
        ${entity} ${entity?uncap_first} = ${table.serviceName?substring(1)?uncap_first}.getById(uuid);
        return ResultInfo.success("获取成功!", ${entity?uncap_first});
    }

    /**
     * 通过主键删除
     *
     * 必须要有<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName?replace("-","_")}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen?replace("-","_")}<#else>${table.entityPath?replace("-","_")}</#if>/delete的权限,否则无法访问
     *
     * @param uuids
     * @return
     */
    @PostMapping("/delete")
    @RequiresPermissions("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName?replace("-","_")}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen?replace("-","_")}<#else>${table.entityPath?replace("-","_")}</#if>/delete")
    public ResultInfo delete(String uuids) {
        if (StringUtils.isNotBlank(uuids)) {
            String[] uuidArray = uuids.split(",");
            boolean result = ${table.serviceName?substring(1)?uncap_first}.removeByIds(Arrays.asList(uuidArray));
            return ResultInfo.autoReturn(result);
        } else {
            return ResultInfo.error("删除失败!");
        }
    }


    /**
     * 分页查询
     *
     * 必须要有<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName?replace("-","_")}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen?replace("-","_")}<#else>${table.entityPath?replace("-","_")}</#if>/listPage的权限,否则无法访问
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    @PostMapping("/listPage")
    @RequiresPermissions("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName?replace("-","_")}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen?replace("-","_")}<#else>${table.entityPath?replace("-","_")}</#if>/listPage")
    public ResultInfo<Page<${entity}>> listPage(@RequestParam(defaultValue = "1") Long pageNum,
                                                @RequestParam(defaultValue = "500")Long pageSize) {
        return ${table.serviceName?substring(1)?uncap_first}.listPage(pageNum,pageSize);
    }
}
</#if>
