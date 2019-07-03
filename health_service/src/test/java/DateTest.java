import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project: PACKAGE_NAME
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-03 11:04
 * @Description:
 * @Version: 1.0
 */
public class DateTest {

    @Test
    public void test01(){
        List<Integer> aaa = new ArrayList<>();
        aaa.add(2);
        Integer integer = aaa.get(0);
        System.out.println(integer);
    }

    @Test
    public void test02(){
        Map<String,Object> bbb = new HashMap<>();
        Object asdf = bbb.get("asdf");
        System.out.println(asdf);
    }
}
