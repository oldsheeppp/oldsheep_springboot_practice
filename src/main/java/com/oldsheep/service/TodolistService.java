package com.oldsheep.service;

import com.oldsheep.entity.Todolist;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oldsheep
 * @since 2021-04-22
 */
@Transactional
public interface TodolistService extends IService<Todolist> {

}
