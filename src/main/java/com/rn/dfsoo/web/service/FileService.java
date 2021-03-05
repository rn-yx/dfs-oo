package com.rn.dfsoo.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rn.dfsoo.web.model.query.FileMetaPageQuery;
import com.rn.dfsoo.web.model.vo.FileMetaVO;
import com.rn.dfsoo.web.model.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务接口
 *
 * @author 然诺
 * @date 2020-04-13
 */
public interface FileService {

	/**
	 * 文件上传
	 *
	 * @param file
	 * @return
	 */
    FileMetaVO upload(MultipartFile file);

	/**
	 * 下载文件
	 *
	 * @param fileId
	 * @return
	 */
	FileVO download(long fileId);

	/**
	 * 删除临时文件
	 *
	 * @param fileId
	 * @param
	 */
	void deleteTempFile(long fileId);

	/**
	 * 文件正式化
	 *
	 * @param fileId
	 * @param bizCode
	 * @return
	 */
	boolean formalTempFile(long fileId, String bizCode);

	/**
	 * 根据ID查询文件元数据
	 *
	 * @param fileId
	 * @return
	 */
    FileMetaVO getFileMetaById(long fileId);

    /**
     * 分页查询文件元数据
     *
     * @param fileMetaPageQuery 分页查询Query
     * @return 分页对象
     */
    IPage<FileMetaVO> getFileMetaPage(FileMetaPageQuery fileMetaPageQuery);

	/**
	 * 根据ID更新文件地址
	 *
	 * @param fileId
	 * @param filePath
	 */
	void updateFilePathById(Long fileId, String filePath);

}