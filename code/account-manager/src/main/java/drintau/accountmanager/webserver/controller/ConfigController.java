package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.shared.BusinessCode;
import drintau.accountmanager.webserver.CommonResult;
import drintau.accountmanager.shared.util.BeanUtil;
import drintau.accountmanager.webserver.domain.bo.ConfigBO;
import drintau.accountmanager.webserver.domain.vo.ConfigAllResVO;
import drintau.accountmanager.webserver.domain.vo.ConfigVO;
import drintau.accountmanager.webserver.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/config")
public class ConfigController {

    private final ConfigService configService;

    @PostMapping("/all")
    public CommonResult<ConfigAllResVO> all() {
        List<ConfigBO> allConfigBOList = configService.allConfig();
        ConfigAllResVO resVO = new ConfigAllResVO();
        resVO.setList(BeanUtil.copyList(allConfigBOList, ConfigVO.class));
        return new CommonResult<>(BusinessCode.SUCCESS, resVO);
    }

}
