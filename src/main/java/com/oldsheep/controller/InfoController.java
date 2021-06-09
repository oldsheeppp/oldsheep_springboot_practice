package com.oldsheep.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oldsheep.entity.Info;
import com.oldsheep.entity.Todolist;
import com.oldsheep.response.RetResponse;
import com.oldsheep.response.RetResult;
import com.oldsheep.service.InfoService;
import com.oldsheep.service.TodolistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldsheep
 * @since 2021-04-17
 */
@RestController
@Api(tags = "Info")
public class InfoController {

    @Autowired
    private InfoService infoService;

    @Autowired
    private TodolistService todolistService;


    @ApiOperation("添加一条Info")
    @PreAuthorize("hasAuthority('post:info')")
    @PostMapping("/infos")
    public String add(@RequestParam("title") String title,
                                 @RequestParam("content") String content,
                                 @RequestParam("username") String username) {
        List<Info> infos = new ArrayList<>();
        Info info = new Info();
        try{
            infos = this.infoService.list();
        }catch (NullPointerException e) {
            info.setInfoId("0");
        }finally {
            if (info.getInfoId() == null) {
                info.setInfoId(String.valueOf(infos.size()));
            }
            info.setTitle(title);
            info.setContent(content);
            info.setUsername(username);
            LocalDate localDate = LocalDate.now();
            info.setStartTime(localDate);
            this.infoService.save(info);
            RetResult<String> stringRetResult = RetResponse.makeOKRsp("success", true, "添加成功");
            String s = JSON.toJSONString(stringRetResult);
            return s;
        }
    }


    @ApiOperation("获取全部Info")
    @PreAuthorize("hasAuthority('get:info')")
    @GetMapping("/infos")
    public String getAll(){
        List<Info> list = null;
        try {
            list = infoService.list();
        }catch (NullPointerException e) {
           // return RetResponse.makeOKRsp("success", true, null);
        }
        RetResult<List<Info>> rsp = RetResponse.makeOKRsp("success", true, list);
        String s = JSON.toJSONString(rsp);
        return s;
    }

    @ApiOperation("通过id修改一条Info")
    @PreAuthorize("hasAuthority('put:info')")
    @PutMapping("/infos/{infoId}")
    public String update(@PathVariable("infoId") String infoId,
                                    @RequestParam("title") String title,
                                    @RequestParam("content") String content,
                                    @RequestParam("username") String username) {
        Info info = this.infoService.getById(infoId);
        info.setUsername(username);
        info.setContent(content);
        info.setTitle(title);
        this.infoService.updateById(info);
        RetResult<String> stringRetResult = RetResponse.makeOKRsp("success", true, "修改成功");
        String s = JSON.toJSONString(stringRetResult);
        return s;
    }

    @ApiOperation("通过id删除一条Info")
    @PreAuthorize("hasAuthority('delete:info')")
    @DeleteMapping("/infos/{infoId}")
    public String delete(@PathVariable("infoId") String infoId) {
        QueryWrapper<Info> wrapper = new QueryWrapper<>();
        wrapper.eq("InfoId", infoId);
        this.infoService.remove(wrapper);
        RetResult<String> stringRetResult = RetResponse.makeOKRsp("success", true, "删除成功");
        String s = JSON.toJSONString(stringRetResult);
        return s;
    }

    @ApiOperation("将Info移动到自己的todolist")
    @PreAuthorize("hasAuthority('move:info')")
    @PostMapping("/infos/{InfoId}/move")
    public String move(@PathVariable("InfoId") String InfoId,
                                  @RequestParam("username") String username) {
        Info info = this.infoService.getById(InfoId);
        Todolist todolist = new Todolist();
        LocalDate localDate = LocalDate.now();
        todolist.setStartTime(localDate);
        todolist.setTitle(info.getTitle());
        todolist.setDone(false);
        todolist.setUsername(username);
        this.todolistService.save(todolist);
        RetResult<String> stringRetResult = RetResponse.makeOKRsp("success", true, "成功加入");
        String s = JSON.toJSONString(stringRetResult);
        return s;
    }

    @ApiOperation("通过id获取一条Info")
    @PreAuthorize("hasAuthority('get:one:info')")
    @GetMapping("/infos/{infoId}")
    public String get(@PathVariable("infoId") String InfoId) {
        Info info = this.infoService.getById(InfoId);
        RetResult<Info> rsp = RetResponse.makeOKRsp("success", true, info);
        String s = JSON.toJSONString(rsp);
        return s;
    }
}

