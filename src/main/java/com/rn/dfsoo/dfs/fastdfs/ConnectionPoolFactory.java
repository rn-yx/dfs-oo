package com.rn.dfsoo.dfs.fastdfs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.IOException;
import java.net.Socket;

/**
 * Description: FastDFS 连接工厂
 *
 * @author 然诺
 * @date 2020/10/21
 */
@Slf4j
public class ConnectionPoolFactory implements PooledObjectFactory<TrackerServer> {

	@Override
	public PooledObject<TrackerServer> makeObject() {
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = null;
		try {
			trackerServer = trackerClient.getConnection();
			int flag = 0;
			while (trackerServer == null && flag < 5) {
				log.info("[创建TrackerServer(createTrackerServer)][第" + flag + "次重建]");
				flag++;
				trackerServer = trackerClient.getConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (trackerServer != null) {
				try {
					trackerServer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return new DefaultPooledObject<>(trackerServer);
	}

	@Override
	public void destroyObject(PooledObject<TrackerServer> pooledObject) throws IOException {
		TrackerServer trackerServer = pooledObject.getObject();
		if (trackerServer != null) {
			trackerServer.close();
		}
	}

	@Override
	public boolean validateObject(PooledObject<TrackerServer> pooledObject) {
		TrackerServer trackerServer = pooledObject.getObject();
		try {
			if (trackerServer != null) {
				Socket socket = trackerServer.getSocket();
				if (ProtoCommon.activeTest(socket)) {
					return Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("validateObject异常:{}", e);
		}
		return false;
	}

	@Override
	public void activateObject(PooledObject<TrackerServer> pooledObject) {
		TrackerServer trackerServer = pooledObject.getObject();
		try {
			if (trackerServer != null) {
				Socket socket = trackerServer.getSocket();
				ProtoCommon.activeTest(socket);
			}
		} catch (Exception e) {
			throw new RuntimeException("activateObject异常:{}", e);
		}
	}

	@Override
	public void passivateObject(PooledObject<TrackerServer> pooledObject) {

	}
}
