package com.rn.dfsoo.dfs;

import java.io.IOException;

/**
 * Description: 分布式文件系统策略接口
 *
 * @author 然诺
 * @date 2020/07/15
 */
public interface DfsOperator {

    /**
     * 上传文件
     *
     * @param fileBuff    文件字节数据
     * @param fileExtName 文件拓展名
     * @return 文件存储路径
     * @throws IOException IO 异常
     */
    String uploadFile(byte[] fileBuff, String fileExtName) throws IOException;

    /**
     * 下载文件
     *
     * @param filePath
     * @return
     */
    byte[] downloadFile(String filePath);

    /**
     * 删除文件
     *
     * @param filePath
     */
    void deleteFile(String filePath);
}
