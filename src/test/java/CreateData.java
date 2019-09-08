import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateData {

    @Test
    public void execute() throws IOException {
        List list = new ArrayList();
        list.add("id;activity_name;jhi_user;telephone;pay_status;add_date");
        for (int i = 1; i <= 500; i++) {
            String s1 = ";" + "让插花艺术成为你的一种生活方式 春风园客 2019第2期花艺培训班;" + "王小" + i + ";" + ("18611910001" + i) + ";Paid;2019-9-8";

            s1 = i + s1;
            list.add(s1);
        }
        System.out.println("list.size() = " + list.size());
        FileUtils.writeLines(new File("/Developer/devops-hackson/chunfeng/src/main/resources/config/liquibase/data/activity_info.csv"), list, "\r\n");
    }

}
