package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.dto.LoginDTO;
import com.example.admin.dto.LoginVO;
import com.example.admin.entity.SysUser;

/**
 * 用户服务接口
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户登录
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 根据用户名查询用户
     */
    SysUser getByUsername(String username);
}
