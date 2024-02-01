package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.QItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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

        if (!StringUtils.hasText(orderSearch.getMemberName())) {
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

    @Override
    public List<Order> findAllWithItem() {
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QDelivery delivery = QDelivery.delivery;
        QOrderItem orderItem = QOrderItem.orderItem;
        QItem item = QItem.item;

        return jpaQueryFactory
                .select(order)
                .distinct()
                .from(order)
                .join(order.member, member)
                .fetchJoin()
                .join(order.delivery, delivery)
                .fetchJoin()
                .join(order.orderItems, orderItem)
                .fetchJoin()
                .join(orderItem.item, item)
                .fetchJoin()
                .fetch();
    }

    @Override
    public List<Order> findAllWithMemberDelivery(Pageable pageable) {
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
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
