package com.company.project.auth.web;

import com.company.project.auth.model.SysRoleMenu;
import com.company.project.auth.service.SysRoleMenuService;
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
@RequestMapping("/sys/role/menu")
public class SysRoleMenuController {

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @PostMapping
    public SysRoleMenu add(@RequestBody SysRoleMenu sysRoleMenu) {
        sysRoleMenuService.save(sysRoleMenu);
        return sysRoleMenu;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sysRoleMenuService.deleteById(id);
    }

    @PutMapping
    public SysRoleMenu update(@RequestBody SysRoleMenu sysRoleMenu) {
        sysRoleMenuService.update(sysRoleMenu);
        return sysRoleMenu;
    }

    @GetMapping("/{id}")
    public SysRoleMenu detail(@PathVariable Long id) {
        return sysRoleMenuService.findById(id);
    }

    @GetMapping
    public PageInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<SysRoleMenu> list = sysRoleMenuService.findAll();
         return new PageInfo(list);
    }
}
