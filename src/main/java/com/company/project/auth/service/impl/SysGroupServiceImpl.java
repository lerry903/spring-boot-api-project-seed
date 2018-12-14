package com.company.project.auth.service.impl;

import com.company.project.auth.dao.SysGroupMapper;
import com.company.project.auth.model.SysGroup;
import com.company.project.auth.service.SysGroupService;
import com.company.project.common.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created with CodeGenerator
 * Description:
 * @author  LErry.li
 * Date: 2018年7月7日
 * Time: 下午7:19:11
 */
@Service
@Transactional
public class SysGroupServiceImpl extends AbstractService<SysGroup> implements SysGroupService {

    @Resource
    private SysGroupMapper sysGroupMapper;

}
