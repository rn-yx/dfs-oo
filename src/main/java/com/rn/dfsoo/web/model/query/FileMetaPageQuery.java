package com.rn.dfsoo.web.model.query;

import com.rn.dfsoo.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 文件元数据分页查询Query
 *
 * @author 然诺
 * @since 2020-04-13
 */
@Getter
@Setter
@ApiModel(description = "FileMeta分页查询Query")
public class FileMetaPageQuery extends BasePageQuery {
}
