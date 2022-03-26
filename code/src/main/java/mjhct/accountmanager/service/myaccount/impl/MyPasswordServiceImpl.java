package mjhct.accountmanager.service.myaccount.impl;

import mjhct.accountmanager.service.myaccount.MyPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MyPasswordServiceImpl implements MyPasswordService {

    private static final Logger logger = LoggerFactory.getLogger(MyPasswordServiceImpl.class);

    private static final List<String> passwordCharList;

    static {
        // 去除一些较难分辨的字符，如：
        // O、o、0、
        // l、|（竖线）、\、/、 I（浏览器显示和小写l一样）
        String[] passwordCharArray = new String[]{
                "A","B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R","S","T","U","V","W","X","Y","Z",
                "a","b","c","d","e","f","g","h","i","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z",
                "1","2","3","4","5","6","7","8","9",
                "@","#","$","%","&","+","-","*","=","_",".",":","?","(",")","[","]","<",">","{","}"
        };
        passwordCharList = List.of(passwordCharArray);
    }

    @Override
    public String getRandomPassword(int digits) {
        StringBuilder randomPasswordBuilder = new StringBuilder();
        String randomChar;
        for (int i=0; i < digits; i++) {
            int index = ThreadLocalRandom.current().nextInt(passwordCharList.size());
            randomChar = passwordCharList.get(index);
            randomPasswordBuilder.append(randomChar);
        }
        return randomPasswordBuilder.toString();
    }
}
