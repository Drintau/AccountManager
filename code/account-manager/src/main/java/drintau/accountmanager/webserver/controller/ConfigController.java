package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.commons.domain.CommonCode;
import drintau.accountmanager.commons.domain.CommonResult;
import drintau.accountmanager.webserver.domain.vo.ConfigQueryReqVO;
import drintau.accountmanager.webserver.domain.vo.ConfigQueryResVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
@CrossOrigin
@Slf4j
public class ConfigController {

    @PostMapping("/query")
    public CommonResult<ConfigQueryResVO> query(@RequestBody @Validated ConfigQueryReqVO reqVO) {
        ConfigQueryResVO resVO = new ConfigQueryResVO();
        return new CommonResult<>(CommonCode.SUCCESS, resVO);
    }

}
