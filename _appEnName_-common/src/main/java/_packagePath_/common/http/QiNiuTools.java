package ${packageName}.common.http;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.yuntao.platform.common.exception.BizException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

public class QiNiuTools {

    private static String QINIU_DOMAIN = "http://res.mynixi.com/";
    private static String ACCESS_KEY = "ALyMvAjXLBD_HMuBPvL2Fm3szQt2J3eng6hEIpn8";
    private static String SECRET_KEY = "fB4ej8RMFNGqmJ_o39X_SQS_nNj8zWp7jfV9ID5o";
    private static String bucket = "nixi";

    static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    public static String getToken() {
        String token = auth.uploadToken(bucket);
        return token;
    }

    public static String getOverToken(String key) {
        String token = auth.uploadToken(bucket, key);
        return token;
    }

    private static String uploadFile(byte[] data, String fileName) {
        String token = getToken();
        UploadManager uploadManager = new UploadManager();
        try {
            String key =
                    DigestUtils.md5Hex(new String(data)).toUpperCase();
            String subffix = getFileSuffix(fileName);
            if (StringUtils.isNotBlank(subffix)) {
                key = key + "." + subffix;
            }
            Response res = uploadManager.put(data, key, token);
            if (res.isOK()) {
                return QINIU_DOMAIN + key;
            }
        } catch (QiniuException e) {
            throw new BizException(e.getMessage(), e);
        }
        return "";
    }

    public static String uploadFileFixName(byte[] data, String fileName) {
        String token = getOverToken(fileName);
        UploadManager uploadManager = new UploadManager();
        try {
            Response res = uploadManager.put(data, fileName, token);
            if (res.isOK()) {
                return QINIU_DOMAIN + fileName;
            }
        } catch (QiniuException e) {
            throw new BizException(e.getMessage(), e);
        }
        return "";
    }


    public static String uploadFile(byte data[]) {
        return uploadFile(data, "");
    }


    private static String getFileSuffix(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        int index = StringUtils.lastIndexOf(fileName, ".");
        return StringUtils.substring(fileName, index + 1, fileName.length());
    }

    public static void main(String[] args) {
        try {
//           System.out.println(new ResourceFacadeImpl().uploadFile(FileUtils.readFileToByteArray(new File("D:\\Artboard 5.png"))));
            //new ResourceFacadeImpl().uploadRecorder();
            byte data[] = FileUtils.readFileToByteArray(new File("C:\\Users\\shan\\Desktop\\d.jpg"));
            String url = QiNiuTools.uploadFile(data);
            System.out.printf("url=" + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
