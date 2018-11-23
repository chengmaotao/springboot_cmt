package com.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.app.entity.Customer;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wsh
 * @since 2018-04-12
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
	Customer selectByUsername(String username);

	Customer selectByShortUrl(String shortUrl);
}
