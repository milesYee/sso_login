package com.miles.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.checkerframework.checker.units.qual.A;

import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_file_attach")
public class FileAttach {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名
     */
    @TableField(value = "file_name", updateStrategy = FieldStrategy.ALWAYS)
    private String fileName;
    /**
     * 文件类型
     */
    @TableField(value = "file_type", updateStrategy = FieldStrategy.ALWAYS)
    private String fileType;
    /**
     * 文件后缀
     */
    @TableField(value = "file_fix", updateStrategy = FieldStrategy.ALWAYS)
    private String fileFix;
    /**
     * 文件路径
     */
    @TableField(value = "file_url", updateStrategy = FieldStrategy.ALWAYS)
    private String fileUrl;
    /**
     * 文件标签
     */
    @TableField(value = "file_tag", updateStrategy = FieldStrategy.ALWAYS)
    private String fileTag;

}
