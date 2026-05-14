package com.example.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.common.result.Result;
import com.example.admin.entity.SysUser;
import com.example.admin.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final SysUserService userService;

    /**
     * 获取用户列表（分页）
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:list')")
    public Result<Page<SysUser>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickname
    ) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getDeleted, 0);
        wrapper.like(username != null && !username.isEmpty(), SysUser::getUsername, username);
        wrapper.like(nickname != null && !nickname.isEmpty(), SysUser::getNickname, nickname);
        wrapper.orderByDesc(SysUser::getCreateTime);

        Page<SysUser> page = userService.page(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(page);
    }

    /**
     * 根据ID获取用户详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:detail')")
    public Result<SysUser> detail(@PathVariable Long id) {
        SysUser user = userService.getById(id);
        if (user == null || user.getDeleted() == 1) {
            return Result.error("用户不存在");
        }
        return Result.success(user);
    }

    /**
     * 创建用户
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('user:add')")
    public Result<Void> create(@RequestBody SysUser user) {
        // 检查用户名是否已存在
        long count = userService.count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, user.getUsername())
                .eq(SysUser::getDeleted, 0));
        if (count > 0) {
            return Result.error("用户名已存在");
        }

        // 设置默认值
        user.setStatus(1);
        user.setDeleted(0);
        // TODO: 密码加密处理
        // user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.save(user);
        return Result.success();
    }

    /**
     * 更新用户
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysUser user) {
        SysUser existUser = userService.getById(id);
        if (existUser == null || existUser.getDeleted() == 1) {
            return Result.error("用户不存在");
        }

        user.setId(id);
        // 不允许修改密码，如需修改请单独实现
        user.setPassword(null);
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public Result<Void> delete(@PathVariable Long id) {
        SysUser user = userService.getById(id);
        if (user == null || user.getDeleted() == 1) {
            return Result.error("用户不存在");
        }

        userService.removeById(id);
        return Result.success();
    }
}
