package com.rn.dfsoo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Description: IO 工具类
 *
 * @author 然诺
 * @date 2019/11/7
 */
public class IOUtils {

    public static final Logger log = LoggerFactory.getLogger(IOUtils.class);

    /**
     * 输出二进制文件
     *
     * @param binaryData
     * @param contentType
     */
    public static void output(byte[] binaryData, String contentType) {
        HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
        if (response == null) {
            return;
        }
        // 重置以便解决冲突
        response.reset();
        if (contentType != null && !"".equals(contentType)) {
	        response.setContentType(contentType + ";charset=UTF-8");
        }
        response.addHeader("Accept-Ranges", "bytes");
        response.setContentLength(binaryData.length);
        output(binaryData, response);
    }

    /**
     * 输出二进制文件
     *
     * @param filePath
     * @param contentType
     */
    public static void output(String filePath, String contentType)  {
        output(getFileBytes(new File(filePath)), contentType);
    }

    /**
     * 下载
     *
     * @param binaryData
     * @param filename
     */
    public static void download(byte[] binaryData, String filename) {
        HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
        if (response == null) {
            return;
        }
        // 重置以便解决冲突
        response.reset();
        response.setContentType("application/octet-stream");
        try {
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("File[" + filename + "] download failed", e);
            return;
        }
        output(binaryData, response);
    }

    /**
     * 下载
     *
     * @param filePath
     * @param filename
     */
    public static void download(String filePath, String filename) {
        download(getFileBytes(new File(filePath)), filename);
    }


    /**
     * 输出
     *
     * @param binaryData
     * @param response
     */
    private static void output(byte[] binaryData, HttpServletResponse response) {
        if (binaryData == null) {
            return;
        }
        OutputStream stream = null;
        try {
            stream = response.getOutputStream();
            stream.write(binaryData);
            stream.flush();
        } catch (IOException ignored) {
            log.error("Failed to output data to client!");
        } finally {
            close(stream);
        }
    }

    /**
     * 关闭流输出流
     *
     * @param stream
     */
    private static void close(OutputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                log.error("Failed to IO!", e);
            }
        }
    }

    /**
     * 获得指定文件的byte数组
     *
     * @param file
     * @return
     */
    public static byte[] getFileBytes(File file) {
        byte[] bytes = null;
        try(FileInputStream fis = new FileInputStream(file); ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            bytes = bos.toByteArray();
        } catch (IOException e) {
            log.error("File getBytes failed", e);
        }
        return bytes;
    }

    /**
     * inputStream转byte[]
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] inputStreamToBytes(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int len = 1024;
        byte[] tmp = new byte[len];
        int i;
        while ((i = is.read(tmp, 0, len)) > 0) {
            os.write(tmp, 0, i);
        }
        return os.toByteArray();
    }
}
