package com.rn.dfsoo.dfs.fastdfs;

import com.rn.dfsoo.dfs.DfsOperator;
import com.rn.dfsoo.dfs.DfsProperties;

import java.io.IOException;

/**
 * Description: FastDFS 操作实现类
 *
 * @author 然诺
 * @date 2020/07/15
 */
public class FastDFSOperator implements DfsOperator {

    private FastDFSClient storageClient;
    private DfsProperties dfsProperties;

    public FastDFSOperator(FastDFSClient storageClient, DfsProperties dfsProperties) {
        this.storageClient = storageClient;
        this.dfsProperties = dfsProperties;
    }

    @Override
    public String uploadFile(byte[] fileBuff, String fileExtName) throws IOException {
        return storageClient.uploadFile(dfsProperties.getGroupName(), fileBuff, fileExtName);
    }

    @Override
    public byte[] downloadFile(String filePath) {
        return storageClient.downloadFile(filePath);
    }

    @Override
    public void deleteFile(String filePath) {
        storageClient.deleteFile(filePath);
    }

}
