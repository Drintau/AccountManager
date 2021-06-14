package mjhct.accountmanager.service.myaccount.impl;

import cn.hutool.core.util.RandomUtil;
import mjhct.accountmanager.service.myaccount.MyPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service(value = "myPasswordService")
public class MyPasswordServiceImpl implements MyPasswordService {

    private static final Logger logger = LoggerFactory.getLogger(MyPasswordServiceImpl.class);

    private static final List<String> passwordCharList = new ArrayList<>(100);

    static {
        // 去除一些较难分辨的字符，如：O、o、0、l、|、\、/ ……
        String[] passwordCharArray = new String[]{
                "A","B","C","D","E","F","G","H","I","J","K","L","M","N","P","Q","R","S","T","U","V","W","X","Y","Z",
                "a","b","c","d","e","f","g","h","i","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z",
                "2","3","4","5","6","7","8","9",
                "@","#","$","%","&","+","-","*","=","_",".",":","?","(",")","[","]","<",">"
        };
        passwordCharList.addAll(Arrays.asList(passwordCharArray));
    }

    @Override
    public String getRandomPassword(int digits) {
        StringBuilder randomPasswordBuilder = new StringBuilder();
        String randomChar;
        for (int i=0; i < digits; i++) {
            int index = RandomUtil.randomInt(passwordCharList.size());
            randomChar = passwordCharList.get(index);
            randomPasswordBuilder.append(randomChar);
        }
        return randomPasswordBuilder.toString();
    }
}
