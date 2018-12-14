package com.company.project.auth.web;

import com.company.project.auth.model.SysUser;
import com.company.project.auth.service.SysUserService;
import com.company.project.common.result.ResponseResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with CodeGenerator
 * Description:
 * @author  LErry.li
 * Date: 2018年7月7日
 * Time: 下午7:19:11
 */
@ResponseResult
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @PostMapping
    public SysUser add(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return sysUser;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sysUserService.deleteById(id);
    }

    @PutMapping
    public SysUser update(@RequestBody SysUser sysUser) {
        sysUserService.update(sysUser);
        return sysUser;
    }

    @GetMapping("/{id}")
    public SysUser detail(@PathVariable Long id) {
        return sysUserService.findById(id);
    }

    @GetMapping
    public PageInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<SysUser> list = sysUserService.findAll();
         return new PageInfo(list);
    }
}
