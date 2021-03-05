package com.rn.dfsoo.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rn.dfsoo.common.ex.BizException;
import com.rn.dfsoo.common.ex.GlobalExCode;
import com.rn.dfsoo.common.ex.Preconditions;
import com.rn.dfsoo.dfs.DfsOperator;
import com.rn.dfsoo.dfs.DfsProperties;
import com.rn.dfsoo.utils.AESUtils;
import com.rn.dfsoo.utils.BeanUtils;
import com.rn.dfsoo.utils.DateUtils;
import com.rn.dfsoo.utils.TransactionUtils;
import com.rn.dfsoo.web.entity.FileMeta;
import com.rn.dfsoo.web.mapper.FileMetaMapper;
import com.rn.dfsoo.web.model.query.FileMetaPageQuery;
import com.rn.dfsoo.web.model.vo.FileMetaVO;
import com.rn.dfsoo.web.model.vo.FileVO;
import com.rn.dfsoo.web.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件服务实现类
 *
 * @author 然诺
 * @date 2020-04-13
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileMetaMapper fileMetaMapper;
    @Resource
    private DfsProperties dfsProperties;
    @Autowired
    private DfsOperator dfsOperator;
    @Autowired
    private TransactionUtils transactionUtils;

    @Override
    public FileMetaVO upload(MultipartFile file) {
        // 校验
        checkFile(file);
        // 存储文件数据
        String storagePath;
        try {
            byte[] fileBytes = file.getBytes();
            // 判断是否加密
            if (dfsProperties.isEnableEncrypt()) {
                fileBytes = AESUtils.encrypt(fileBytes, dfsProperties.getSecretKey());
            }
            // 上传数据文件到存储系统
            storagePath = dfsOperator.uploadFile(fileBytes, FilenameUtils.getExtension(file.getOriginalFilename()));
        } catch (Exception e) {
            log.error("保存文件字节数据到文件存储系统失败", e);
            throw new BizException(GlobalExCode.FILE_WRITE_FAIL);
        }
        // 持久化文件元数据
        FileMeta fileMeta = saveFileMeta(file, storagePath);
        if (fileMeta == null) {
            // TODO: 清除已存储数据
            throw new BizException(GlobalExCode.ACTION_FAILURE);
        }
        return BeanUtils.copyProperties(fileMeta, FileMetaVO.class);
    }

    @Override
    public FileVO download(long fileId) {
        // 查询文件元数据信息
        FileMeta fileMeta = Preconditions.checkNotNull(fileMetaMapper.selectById(fileId), GlobalExCode.ILLEGAL_OPERATE);
        // 获取文件数据
        byte[] bytes = dfsOperator.downloadFile(fileMeta.getPath());
        // 解密文件
        if (dfsProperties.isEnableEncrypt()) {
            try {
                bytes = AESUtils.decrypt(bytes, AESUtils.decrypt(fileMeta.getAbeSecretKey()));
            } catch (Exception e) {
                log.error("文件[{}]解密失败", fileId, e);
                return null;
            }
        }
        FileVO fileVO = new FileVO();
        fileVO.setFileBytes(bytes);
        fileVO.setFileName(fileMeta.getFileName());
        fileVO.setSuffix(fileMeta.getSuffix());
        return fileVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTempFile(long fileId) {
        FileMetaVO fileMetaVO = getFileMetaById(fileId);
        if (fileMetaVO != null) {
            // 删除文件数据
            dfsOperator.deleteFile(fileMetaVO.getPath());
            // 删除文件元数据信息
            fileMetaMapper.deleteById(fileId);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean formalTempFile(long fileId, String bizCode) {
        // 检查文件
        FileMeta fileMeta = fileMetaMapper.selectById(fileId);
        if (fileMeta == null || !fileMeta.getIsTemp()) {
            return false;
        }
        fileMeta.setBizCode(bizCode);
        fileMeta.setIsTemp(false);
        fileMeta.setUpdateTime(DateUtils.currentTime());

        // 修改文件标记
        fileMetaMapper.updateById(fileMeta);
        return false;
    }

    @Override
    public FileMetaVO getFileMetaById(long fileId) {
        return fileMetaMapper.selectFileMetaById(fileId);
    }

    @Override
    public IPage<FileMetaVO> getFileMetaPage(FileMetaPageQuery fileMetaPageQuery) {
        return fileMetaMapper.selectFileMetaPage(fileMetaPageQuery.page());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFilePathById(Long fileId, String filePath) {
        fileMetaMapper.updateFilePathById(fileId, filePath);
    }

    /**
     * 校验文件
     */
    private void checkFile(MultipartFile file) {
        Preconditions.checkTrue(file != null, GlobalExCode.FILE_IS_EMPTY);
        Preconditions.checkTrue(file.getSize() <= dfsProperties.getMaxFileSize(), GlobalExCode.FILE_SIZE_EXCEEDED_MAX_LIMIT);
    }

    /**
     * 保存文件元数据信息
     */
    private FileMeta saveFileMeta(MultipartFile file, String filePath) {
        FileMeta fileMeta = new FileMeta();
        String fileName = file.getOriginalFilename();
        try {
            fileMeta.setMdHash(DigestUtils.md5Hex(file.getInputStream()));
            fileMeta.setShaHash(DigestUtils.sha256Hex(file.getInputStream()));
        } catch (Exception e) {
            log.error("生成文件[{}]的摘要失败", fileName, e);
            return null;
        }
        fileMeta.setIsEncrypt(dfsProperties.isEnableEncrypt());
        if (fileMeta.getIsEncrypt()) {
            try {
                fileMeta.setAbeSecretKey(AESUtils.encrypt(dfsProperties.getSecretKey()));
            } catch (Exception e) {
                log.error("保存文件元数据信息->属性加密对称密钥失败", e);
                return null;
            }
        }
        fileMeta.setFileName(fileName);
        fileMeta.setSuffix(FilenameUtils.getExtension(fileName));
        fileMeta.setSize(file.getSize());
        fileMeta.setPath(filePath);
        // 持久化数据（手动提交事务：将事务与文件写入、摘要计算等分开，避免产生大事务）
        transactionUtils.transact(c -> fileMetaMapper.insert(fileMeta));
        return fileMeta;
    }

}