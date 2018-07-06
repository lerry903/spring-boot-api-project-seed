package com.company.project.web;

import com.company.project.BaseTestController;


public class UserControllerTest extends BaseTestController {


    /*
    @Test
    public void add() throws Exception{
        User user = new User();
        user.setUsername("lerry");
        user.setPassword("111111");
        user.setNickName("lerry.li");
        user.setSex(1);
        user.setRegisterDate(new Date());
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
    */
}