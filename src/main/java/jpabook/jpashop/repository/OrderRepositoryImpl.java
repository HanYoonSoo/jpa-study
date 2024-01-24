package jpabook.jpashop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderCustom{


    private final JPAQueryFactory jpaQueryFactory;

    QOrder order = QOrder.order;
    QMember member = QMember.member;

    @Override
    public List<Order> findAll(OrderSearch orderSearch) {
        return jpaQueryFactory
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(order.status.eq(orderSearch.getOrderStatus()),
                        member.name.eq(orderSearch.getMemberName()))
                .fetch();
    }
}
