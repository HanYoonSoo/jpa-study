package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.QDelivery;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderCustom{


    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Order> findAll(OrderSearch orderSearch) {
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        BooleanBuilder builder = new BooleanBuilder();

        if(orderSearch.getOrderStatus() != null){
            builder.and(order.status.eq(orderSearch.getOrderStatus()));
        }

        if (orderSearch.getMemberName() != null && !orderSearch.getMemberName().isEmpty()) {
            builder.and(member.name.eq(orderSearch.getMemberName()));
        }

        return jpaQueryFactory
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(builder)
                .fetch();
    }

    @Override
    public List<Order> findAllWithMemberDelivery() {
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QDelivery delivery = QDelivery.delivery;

        return jpaQueryFactory
                .select(order)
                .from(order)
                .join(order.member, member)
                .fetchJoin()
                .join(order.delivery, delivery)
                .fetchJoin()
                .fetch();
    }
}
