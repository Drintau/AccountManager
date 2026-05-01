package drintau.accountmanager.webserver.service.impl;

import drintau.accountmanager.shared.util.RandomUtil;
import drintau.accountmanager.webserver.config.ConfigKeyConstant;
import drintau.accountmanager.webserver.service.ConfigService;
import drintau.accountmanager.webserver.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PasswordServiceImpl implements PasswordService {

    private final ConfigService configService;

    /*
    去除一些较难分辨的字符，如：
    O、o、0、
    l、|（竖线）、\、/、 I（浏览器显示和小写l一样）

    然后去掉不好看的，如:
    -、_、:
     */
    private static final char[] PASSWORD_CHARS = new char[]{
            'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z',
            '1','2','3','4','5','6','7','8','9',
            '@','#','&','+','*','=','?'
    };

    @Override
    public String getRandomPassword() {
        String configValue = configService.getConfigValue(ConfigKeyConstant.PASSWORD_LENGTH);
        return getRandomPassword(Integer.parseInt(configValue));
    }

    @Override
    public String getRandomPassword(int length) {
        StringBuilder randomPasswordBuilder = new StringBuilder(length);
        char randomChar;
        for (int i=0; i < length; i++) {
            int index = RandomUtil.randomInt(PASSWORD_CHARS.length);
            randomChar = PASSWORD_CHARS[index];
            randomPasswordBuilder.append(randomChar);
        }
        return randomPasswordBuilder.toString();
    }

}
