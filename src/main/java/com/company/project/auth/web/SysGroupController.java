package com.company.project.auth.web;

import com.company.project.auth.model.SysGroup;
import com.company.project.auth.service.SysGroupService;
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
@RequestMapping("/sys/group")
public class SysGroupController {

    @Resource
    private SysGroupService sysGroupService;

    @PostMapping
    public SysGroup add(@RequestBody SysGroup sysGroup) {
        sysGroupService.save(sysGroup);
        return sysGroup;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sysGroupService.deleteById(id);
    }

    @PutMapping
    public SysGroup update(@RequestBody SysGroup sysGroup) {
        sysGroupService.update(sysGroup);
        return sysGroup;
    }

    @GetMapping("/{id}")
    public SysGroup detail(@PathVariable Long id) {
        return sysGroupService.findById(id);
    }

    @GetMapping
    public PageInfo list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<SysGroup> list = sysGroupService.findAll();
         return new PageInfo(list);
    }
}
