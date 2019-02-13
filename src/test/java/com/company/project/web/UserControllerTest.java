package com.company.project.web;

import com.alibaba.fastjson.JSONObject;
import com.company.project.BaseTestController;
import com.company.project.auth.model.SysUser;
import org.junit.Test;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


public class UserControllerTest extends BaseTestController {



    @Test
    public void add() throws Exception{
        SysUser user = new SysUser();
        user.setUsername("lerry");
        user.setPassword("111111");
        RequestBuilder requestBuilder = post("/user").content(JSONObject.toJSONString(user));
        String response = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }

    @Test
    public void delete() throws Exception {
        RequestBuilder requestBuilder = org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/user/{id}",10);
        String response = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }

    @Test
    public void list() throws Exception {
        RequestBuilder requestBuilder = get("/user");
        String response = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }

}