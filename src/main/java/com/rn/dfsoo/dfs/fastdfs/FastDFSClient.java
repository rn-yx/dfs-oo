package com.rn.dfsoo.dfs.fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerServer;

import java.io.IOException;

/**
 * Description: FastDFS 客户端
 *
 * @author 然诺
 * @date 2020/10/21
 */
public class FastDFSClient {

    private ConnectionPool pool;

    public FastDFSClient(ConnectionPool pool) {
        this.pool = pool;
    }

    /**
     * 上传文件
     *
     * @param groupName   分组名称
     * @param fileBuff    文件字节数据
     * @param fileExtName 文件拓展名
     * @return 文件路径
     */
    public String uploadFile(String groupName, byte[] fileBuff, String fileExtName) throws IOException {
        String path;
        TrackerServer trackerServer = pool.borrowObject();
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        try {
            path = storageClient.upload_file1(groupName, fileBuff, fileExtName, null);
        } catch (MyException e) {
            throw new RuntimeException("Failed to upload file!", e);
        } finally {
            pool.returnObject(trackerServer);
        }
        return path;
    }

    public byte[] downloadFile(String filePath) {
        TrackerServer trackerServer = pool.borrowObject();
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        byte[] fileByte = new byte[0];
        try {
            fileByte = storageClient.download_file1(filePath);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
        pool.returnObject(trackerServer);
        return fileByte;
    }

    public void deleteFile(String filePath) {
        TrackerServer trackerServer = pool.borrowObject();
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        try {
            storageClient.delete_file1(filePath);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
        pool.returnObject(trackerServer);
    }

}
