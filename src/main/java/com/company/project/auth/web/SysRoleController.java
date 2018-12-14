package com.company.project.auth.web;

import com.company.project.auth.model.SysRole;
import com.company.project.auth.service.SysRoleService;
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
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @PostMapping
    public SysRole add(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return sysRole;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sysRoleService.deleteById(id);
    }

    @PutMapping
    public SysRole update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
        return sysRole;
    }

    @GetMapping("/{id}")
    public SysRole detail(@PathVariable Long id) {
        return sysRoleService.findById(id);
    }

    @GetMapping
    public PageInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<SysRole> list = sysRoleService.findAll();
         return new PageInfo(list);
    }
}
