package com.github.yuzhian.zero.server.system.controller;

import com.github.yuzhian.zero.server.common.util.MD5Utils;
import com.github.yuzhian.zero.server.system.dto.AuthenticationDTO;
import com.github.yuzhian.zero.server.system.service.ISysAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author yuzhian
 * @since 2020-10-30
 */
@Profile(value = "dev")
@Api(tags = "功能测试控制器")
@RestController
@RequestMapping(value = "/hello")
@RequiredArgsConstructor
public class HelloController {
    private final ISysAccountService accountService;
    private final RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "mvc 测试")
    @ApiImplicitParam(name = "msg", value = "接受信息", paramType = "path", dataType = "java.lang.String")
    @GetMapping(value = "/talk/{msg}")
    public ResponseEntity<String> talk(@PathVariable(value = "msg", required = false) String msg) {
        return ResponseEntity.ok(msg.replaceAll("吗", "").replaceAll("？", "！"));
    }

    @ApiOperation(value = "mybatis 测试")
    @ApiImplicitParam(name = "account", value = "帐号(用户名/邮箱/手机号)", required = true, paramType = "path", dataType = "java.lang.String")
    @GetMapping(value = "/user/{account}")
    public ResponseEntity<AuthenticationDTO> getAccount(@PathVariable @NotEmpty(message = "必填参数不可为空") String account) {
        return ResponseEntity.ok(accountService.getAuthentication(account));
    }

    @ApiOperation(value = "md5 测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "plaintext", value = "明文", required = true, paramType = "query", dataType = "java.lang.String"),
            @ApiImplicitParam(name = "salt", value = "盐", paramType = "query", dataType = "java.lang.String")
    })
    @GetMapping(value = "/md5")
    public ResponseEntity<Object> password(@RequestParam String plaintext,
                                           @RequestParam(required = false) String salt) {
        return ResponseEntity.ok(StringUtils.isEmpty(salt)
                ? MD5Utils.generatePassword(plaintext)
                : MD5Utils.encryptPassword(plaintext, salt));
    }


    @ApiOperation(value = "redis 测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "path", dataType = "java.lang.String"),
            @ApiImplicitParam(name = "val", value = "值", required = true, paramType = "path", dataType = "java.lang.String")
    })
    @PostMapping(value = "/redis/{key}/{val}")
    public ResponseEntity<Object> saveRedis(@PathVariable @NotEmpty(message = "key不可为空") String key,
                                            @PathVariable @NotEmpty(message = "val不可为空") String val) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, val, 20, TimeUnit.SECONDS);
        return ResponseEntity.ok(Objects.requireNonNull(ops.get(key)));
    }

    @ApiOperation(value = "权限1 测试")
    @RequiresPermissions(value = "test:test:test1")
    @GetMapping(value = "test1")
    public ResponseEntity<String> test1() {
        return ResponseEntity.ok("test:test:test1");
    }

    @ApiOperation(value = "权限2 测试")
    @RequiresPermissions(value = "test:test:test2")
    @GetMapping(value = "test2")
    public ResponseEntity<String> test2() {
        return ResponseEntity.ok("test:test:test2");
    }

}
