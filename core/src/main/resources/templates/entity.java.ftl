<#setting number_format="computer">
package ${package.Entity};
<#--这里处理数据权限注解-->
<#if containsDataAuth>
import com.jhf.core.annotation.DataAuthBean;
import com.jhf.core.annotation.DataAuthField;
</#if>
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.*;
import com.baomidou.mybatisplus.annotation.*;
<#list table.importPackages as pkg>
    <#if !pkg?contains("com.baomidou.mybatisplus.annotation") && !pkg?contains("java.io.Serializable")>
import ${pkg};
    </#if>
</#list>
<#if swagger>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.*;
    <#if chainModel>
import lombok.experimental.Accessors;
    </#if>
</#if>

/**
 * ${table.comment!}
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
<#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
<#else>
@EqualsAndHashCode(callSuper = false)
</#if>
    <#if chainModel>
@Accessors(chain = true)
    </#if>
</#if>
<#if table.convert>
@TableName("${schemaName}${table.name}")
</#if>
<#if swagger>
@ApiModel(value = "${entity}对象", description = "${table.comment!}")
</#if>
<#--这里处理数据权限注解-->
<#if containsDataAuth>
@DataAuthBean(name = "${table.comment!}",className = "${entity}",physicalName = "${table.name}")
</#if>
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
<#elseif activeRecord>
public class ${entity} extends Model<${entity}> {
<#elseif entitySerialVersionUID>
public class ${entity} implements Serializable {
<#else>
public class ${entity} {
</#if>
<#if entitySerialVersionUID>

    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger>
    @ApiModelProperty("${field.comment}")
        <#else>
    /**
     * ${field.comment}
     */
        </#if>
    </#if>
    <#if field.keyFlag>
        <#-- 主键 -->
        <#if field.keyIdentityFlag>
    @TableId(value = "${field.annotationColumnName}", type = IdType.AUTO)
        <#elseif idType??>
    @TableId(value = "${field.annotationColumnName}", type = IdType.${idType})
        <#elseif field.convert>
    @TableId("${field.annotationColumnName}")
        </#if>
        <#-- 普通字段 -->
    <#elseif field.fill??>
    <#-- -----   存在字段填充设置   ----->
        <#if field.convert>
    @TableField(value = "${field.annotationColumnName}", fill = FieldFill.${field.fill})
        <#else>
    @TableField(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
        <#if updateStrategy && field.metaInfo.nullable>
    @TableField(value = "${field.annotationColumnName}", updateStrategy = FieldStrategy.ALWAYS)
        <#else>
    @TableField("${field.annotationColumnName}")
        </#if>
    </#if>
    <#-- 乐观锁注解 -->
    <#if field.versionField>
    @Version
    </#if>
    <#-- 逻辑删除注解 -->
    <#if field.logicDeleteField>
    @TableLogic
    </#if>
    <#if !field.metaInfo.nullable && field.propertyName != 'id'>
        <#if field.propertyType=='String'>
    @NotBlank(message = "${field.comment?split('|')[0]}不能为空")
        <#elseif ['Long','Integer','Boolean','Date','BigDecimal']?seq_contains(field.propertyType)>
    @NotNull(message = "${field.comment?split('|')[0]}不能为空")
        </#if>
    </#if>
    <#if field.propertyType=='String'>
    @Size(max = ${field.metaInfo.length},message = "${field.comment?split('|')[0]}超过最大长度${field.metaInfo.length}")
    </#if>
    <#if field.metaInfo.typeName=='DATETIME'>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    </#if>
    <#if field.metaInfo.typeName=='DATE'>
    @JsonFormat(pattern = "yyyy-MM-dd")
    </#if>
    <#--这里处理数据权限注解-->
    <#if containsDataAuth >
        <#if !excludeFields?seq_contains(field.annotationColumnName)>
    @DataAuthField(name = "${field.comment?split('|')[0]}",fieldName = "${field.propertyName}",physicalName = "${field.annotationColumnName}",sort =${field_index+1},dataType = "${field.propertyType?replace("Byte","Integer")}")
        </#if>
    </#if>
    private ${field.propertyType?replace("Byte","Integer")} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

    <#if chainModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if chainModel>
        return this;
        </#if>
    }
    </#list>
</#if>

<#if entityColumnConstant>
    <#list table.fields as field>
    public static final String ${field.name?upper_case} = "${field.name}";

    </#list>
</#if>
<#if activeRecord>
    @Override
    public Serializable pkVal() {
    <#if keyPropertyName??>
        return this.${keyPropertyName};
    <#else>
        return null;
    </#if>
    }

</#if>
<#if !entityLombokModel>
    @Override
    public String toString() {
        return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
        "}";
    }
</#if>
}
