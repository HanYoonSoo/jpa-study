package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCustom {
    public List<Order> findAll(@Param("orderSearch") OrderSearch orderSearch);
}
