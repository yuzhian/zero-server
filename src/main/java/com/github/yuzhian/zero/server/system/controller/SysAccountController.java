package com.github.yuzhian.zero.server.system.controller;


import com.github.yuzhian.zero.server.system.dto.LoginDTO;
import com.github.yuzhian.zero.server.system.dto.RegisterDTO;
import com.github.yuzhian.zero.server.system.service.ISysAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 帐号 前端控制器
 * </p>
 *
 * @author yuzhian
 * @since 2020-10-30
 */
@Slf4j
@Api(tags = "账户控制器")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/system/account")
public class SysAccountController {
    private final ISysAccountService accountService;

    @ApiOperation(value = "登录接口")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(dto.getAccount(), dto.getPassword());
            subject.login(usernamePasswordToken);
            String token = subject.getSession().getId().toString();
            log.info("login success, token: {}", token);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("帐号或密码错误", HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation(value = "注册接口")
    @PutMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO dto) {
        boolean flag = accountService.register(dto);
        return new ResponseEntity<>(flag ? HttpStatus.OK : HttpStatus.FORBIDDEN);
    }

    @ApiOperation(value = "注销接口")
    @DeleteMapping("/logout")
    public ResponseEntity<String> logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return ResponseEntity.ok("成功退出");
    }

}
