package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustom {
    public Order findOrderById(Long id);

    @Query("select new jpabook.jpashop.repository.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
            "from Order o" +
            " join o.member m" +
            " join o.delivery d")
    public List<OrderSimpleQueryDto> findOrderDtos();
}

