package com.myshop.site.customer;

import com.myshop.common.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,Integer> {
}
