import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author huangchangjun
 * @date 2023/11/29
 */
@Data
@Slf4j
public class FileTest {
    public static void main(String[] args) {
        try {
            log.info("开始进行文件序列化异常测试！");
            FileTest fileTest = new FileTest();
            UploanFileDTO uploanFileDTO = fileTest.new UploanFileDTO();
            uploanFileDTO.setId("1");
            uploanFileDTO.setFileName("testFile");
            File file = new File("E:\\java\\fileTest.txt");
            if (file.exists()) {
                log.info("该文件存在，文件名为={}", file.getName());
                uploanFileDTO.setFile(file);
            } else {
                log.info("该文件不存在!");
            }
            byte[] fileBytes = FileUtils.readFileToByteArray(file);
            uploanFileDTO.setFileBytes(fileBytes);

            log.info("对DTO进行序列化开始 ，uploanFileDTO = {}", JSON.toJSON(uploanFileDTO));
            log.info("对DTO进行序列化开始 ，fileBytes = {}", JSON.toJSON(fileBytes));
            JSON.toJSONString(uploanFileDTO);
            log.info("对DTO进行序列化结束 ，uploanFileDTO = {}", JSON.toJSONString(uploanFileDTO));
            log.info("对DTO进行序列化开始 ，fileBytes = {}", JSON.toJSONString(fileBytes));
        } catch (Exception e) {
            log.error("测试类出现异常,message", e.getMessage(), e);
        }
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    class UploanFileDTO {
        private String id;
        private String fileName;
        private File file;
        private byte[] fileBytes;
    }
}
