package com.oldsheep.service.impl;

import com.oldsheep.entity.Info;
import com.oldsheep.mapper.InfoMapper;
import com.oldsheep.service.InfoService;
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
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {

}
