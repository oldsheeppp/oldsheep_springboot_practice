package com.oldsheep.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oldsheep.entity.*;
import com.oldsheep.service.*;
import com.oldsheep.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldsheep
 * @since 2021-04-17
 */
@RestController
@Api(tags = "TodolistController")
public class TodolistController {

    @Autowired
    private TodolistService todolistService;

    @PostMapping("/todolist")
    @ApiOperation("添加一条todo")
    public String add(@RequestParam("username") String username,
                                 @RequestParam("title") String title) {
        if (username == null || title == null) {
            RetResult<Object> rsp = RetResponse.makeErrRsp("没有标题", false, null);
            String s = JSON.toJSONString(rsp);
            return s;
        }
        List<Todolist> list = this.todolistService.list();
        Todolist todolist = new Todolist();
        LocalDate localDate = LocalDate.now();
        todolist.setTodoId(String.valueOf(list.size()));
        todolist.setUsername(username);
        todolist.setDone(false);
        todolist.setTitle(title);
        todolist.setStartTime(localDate);
        todolistService.save(todolist);
        RetResult<String> stringRetResult = RetResponse.makeOKRsp("success", true, "成功加入");
        String s = JSON.toJSONString(stringRetResult);
        return s;
    }

    @PutMapping("/todolist/{todolistid}")
    @ApiOperation("通过id将一条todo设置为已完成")
    public String done(@PathVariable("todolistid") String id) {
        Todolist todolist = this.todolistService.getById(id);
        if (todolist.getDone()) {
            RetResult<Object> rsp = RetResponse.makeOKRsp("已经完成过了", false, null);
            String s = JSON.toJSONString(rsp);
            return s;
        }
        else {
            todolist.setDone(true);
            this.todolistService.save(todolist);
            RetResult<String> stringRetResult = RetResponse.makeOKRsp("success", true, "任务完成");
            String s = JSON.toJSONString(stringRetResult);
            return s;
        }
    }

    @GetMapping("/todolist")
    @ApiOperation("通过用户名获取全部的todo")
    public String getTodolist(@RequestParam("username") String username) {
        List<Todolist> list = new ArrayList<>();
        try{
            Map<String, Object> map = new HashMap<>();
            map.put("Username", username);
            list = this.todolistService.listByMap(map);
        }catch (NullPointerException e) {
            RetResult<Object> success = RetResponse.makeOKRsp("success", true, null);
            String s = JSON.toJSONString(success);
            return s;
        }finally {
            RetResult<List<Todolist>> success = RetResponse.makeOKRsp("success", true, list);
            String s = JSON.toJSONString(success);
            return s;
        }
    }

    @DeleteMapping("/todolist/{todoId}")
    @ApiOperation("通过id删除一条todo")
    public String delete(@PathVariable("todoId") String id) {
        this.todolistService.removeById(id);
        RetResult<String> stringRetResult = RetResponse.makeOKRsp("success", true, "删除成功");
        String s = JSON.toJSONString(stringRetResult);
        return s;
    }

    @GetMapping("/todolist/page/{num}")
    @ApiOperation("分页获取todo")
    public String getPage(@PathVariable("num") long num) {
        Page<Todolist> page = new Page<>(num, 5);
        todolistService.page(page, null);
        List<Todolist> records = page.getRecords();
        RetResult<List<Todolist>> success = RetResponse.makeOKRsp("success", true, records);
        String s = JSON.toJSONString(success);
        return s;
    }
}

