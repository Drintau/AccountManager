package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.domain.CommonResult;
import drintau.accountmanager.webserver.domain.vo.ConfigListResVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@CrossOrigin
@Slf4j
public class ConfigController {

    @PostMapping("/query")
    public CommonResult<ConfigListResVO> query() {
        ConfigListResVO resVO = new ConfigListResVO();
        return new CommonResult<>(CommonCode.SUCCESS, resVO);
    }

}
