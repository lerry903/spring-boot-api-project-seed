package com.company.project.service;

import com.company.project.BaseTestService;
import com.company.project.auth.model.SysUser;
import com.company.project.auth.service.SysUserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends BaseTestService {


    @Autowired
    private SysUserService userService;

    @Test
    public void findOne() throws Exception {
        SysUser user = userService.findById(1L);
        Assert.assertEquals(Long.valueOf(1), user.getId());
    }


}