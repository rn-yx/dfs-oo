package com.rn.dfsoo.dfs.localdfs;

import com.rn.dfsoo.common.constant.GlobalConstants;
import com.rn.dfsoo.common.ex.BizException;
import com.rn.dfsoo.common.ex.GlobalExCode;
import com.rn.dfsoo.dfs.DfsOperator;
import com.rn.dfsoo.utils.IOUtils;
import com.rn.dfsoo.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Description: 本地存储DFS操作实现类
 *
 * @author 然诺
 * @date 2020/07/15
 */
@Slf4j
public class LocalDFSOperator implements DfsOperator {

    /**
     * 临时文件上传路径
     */
    private final static String TEMP_FILE_PATH = "data/temp/";

    @Override
    public String uploadFile(byte[] fileBuff, String fileExtName) throws IOException {
        // 临时文件存放路径
        String storagePath = TEMP_FILE_PATH + UUIDUtils.genUuid() + GlobalConstants.PERIOD_SEP + fileExtName;
        // 存储文件到本地临时文件目录
        Files.createDirectories(Paths.get(TEMP_FILE_PATH));
        Path path = Paths.get(storagePath);
        Files.deleteIfExists(path);
        Files.createFile(path);
        FileUtils.writeByteArrayToFile(path.toFile(), fileBuff);
        return storagePath;
    }

    @Override
    public byte[] downloadFile(String filePath) {
        File file = new File(filePath);
        return (file.exists() && file.isFile()) ? IOUtils.getFileBytes(file) : new byte[0];
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            File tempFile = new File(filePath);
            FileUtils.forceDeleteOnExit(tempFile);
        } catch (IOException e) {
            log.error("删除临时文件[{}]失败 : {}", filePath, e.getMessage(), e);
            throw new BizException(GlobalExCode.FILE_DELETE_FAIL);
        }
    }

}
