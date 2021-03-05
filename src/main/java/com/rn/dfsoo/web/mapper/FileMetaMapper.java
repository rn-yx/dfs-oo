package com.rn.dfsoo.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rn.dfsoo.web.model.vo.FileMetaVO;
import com.rn.dfsoo.web.entity.FileMeta;
import org.apache.ibatis.annotations.Param;

/**
 * 文件元数据Mapper 接口
 *
 * @author 然诺
 * @since 2020-04-13
 */
public interface FileMetaMapper extends BaseMapper<FileMeta> {

    /**
     * 根据ID查询文件元数据
     *
     * @param id 文件元数据ID
     * @return 文件元数据对象
     */
    FileMetaVO selectFileMetaById(long id);

    /**
     * 查询分页列表
     *
     * @param page 分页查询对象
     * @return 返回对象列表
     */
    IPage<FileMetaVO> selectFileMetaPage(Page page);

	/**
	 * 根据ID更新文件地址
	 *
	 * @param fileId
	 * @param filePath
	 */
	void updateFilePathById(@Param("fileId") Long fileId, @Param("filePath") String filePath);
}
