package test;

import com.example.client.client.Client;
import com.example.client.model.DownloadFile;
import org.apache.commons.io.FileUtils;
import org.junit.Test;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ClientTest {
    public static void main(String[] args) throws Exception {
        Client.init("127.0.0.1", 2688);
        String filePath = "d:\\testdss\\1.txt";
        File file = new File(filePath);
        Map<String, String> metaData = new HashMap<>();
        String result = Client.upload(file, metaData);
        System.out.println("文件上传成功: "+result);

    }
    @Test
    public void download() {
        String fileId = "b738b00d6a23c220938e728fd3da3ab6";
        try {
            Client.init("127.0.0.1", 2688);
            DownloadFile download = Client.download(fileId);
            File file = new File("d:\\testdss\\2.txt");
            FileUtils.copyInputStreamToFile(download.getFileInputStream(),file);
            System.out.println("文件下载成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
