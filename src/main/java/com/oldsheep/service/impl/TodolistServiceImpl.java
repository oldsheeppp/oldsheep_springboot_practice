package com.oldsheep.service.impl;

import com.oldsheep.entity.Todolist;
import com.oldsheep.mapper.TodolistMapper;
import com.oldsheep.service.TodolistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oldsheep
 * @since 2021-04-22
 */
@Service
public class TodolistServiceImpl extends ServiceImpl<TodolistMapper, Todolist> implements TodolistService {

}
