package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findOrderById(Long id);


    @Query("select o from Order o join o.member m where o.status = :#{#orderSearch.orderStatus} and m.name = #{#orderSearch.memberName}")
    public List<Order> findAll(@Param("orderSearch") OrderSearch orderSearch);
}

