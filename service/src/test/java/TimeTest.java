import com.alibaba.fastjson.JSON;

/**
 * @author huangchangjun
 * @date
 */
public class TimeTest {

    public static void main(String[] args) {
//        long currentTime = System.currentTimeMillis();
//        System.out.println(currentTime);
//
//        long date = 3 * 24 * 60 * 60;
//        System.out.println(date);
//        long resultTime = currentTime - date;
//        System.out.println(resultTime);

        String str ="{serverFileName:'" + 123 +"'}";
        Object object = JSON.toJSON(str);
        System.out.println(object.toString());
    }
}
