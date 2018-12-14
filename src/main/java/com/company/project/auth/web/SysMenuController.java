package com.company.project.auth.web;

import com.company.project.auth.model.SysMenu;
import com.company.project.auth.service.SysMenuService;
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
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @PostMapping
    public SysMenu add(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return sysMenu;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sysMenuService.deleteById(id);
    }

    @PutMapping
    public SysMenu update(@RequestBody SysMenu sysMenu) {
        sysMenuService.update(sysMenu);
        return sysMenu;
    }

    @GetMapping("/{id}")
    public SysMenu detail(@PathVariable Long id) {
        return sysMenuService.findById(id);
    }

    @GetMapping
    public PageInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<SysMenu> list = sysMenuService.findAll();
         return new PageInfo(list);
    }
}
