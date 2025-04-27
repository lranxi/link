package com.lyranxi.link.member.biz;

import com.lyranxi.link.member.entity.Customer;
import com.lyranxi.link.member.mapper.CustomerMapper;
import com.lyranxi.link.mysql.biz.BaseBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ranxi
 * @date 2025-04-27 17:58
 */
@Slf4j
@Service
public class CustomerBiz extends BaseBiz<CustomerMapper, Customer> {
}
