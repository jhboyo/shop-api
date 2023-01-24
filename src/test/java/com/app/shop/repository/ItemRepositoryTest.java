package com.app.shop.repository;

import com.app.common.BaseTest;
import com.app.shop.constant.ItemSellStatus;
import com.app.shop.entity.Item;
import com.app.shop.entity.QItem;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

class ItemRepositoryTest extends BaseTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        for (int i=1; i<=10; i++) {
            Item item = new Item();
            item.setItemName("테스트상품"+i);
            item.setPrice(100000);
            item.setItemDetail("테스트상품상세설명"+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 조회 테스트")
    public void findByItemNameTest() {
        this.createItemTest();
        final var itemList = itemRepository.findByItemName("테스트상품1");

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
    
    
    @Test
    @DisplayName("상풍명, 상품상세설명 or 테스트")
    public void findByItemNameOrItemDetailTest() {
        this.createItemTest();
        final var itemList = itemRepository.findByItemNameOrItemDetail("테스트상품1", "테스트상품상세설명5");

        itemList.forEach(s -> System.out.println(s.toString()));
    }

    @Test
    @DisplayName("@Queryfㄹmㄹ 이용한 상품 조회 테스트")
    public void findByDetailTest() {
        this.createItemTest();
        final var itemDetail = itemRepository.findByItemDetail("테스트상품상세설명");

        itemDetail.forEach(s -> System.out.println(s.toString()));
    }

    @Test
    @DisplayName("Querydsl조회테스트")
    public void querydslTest() {
        this.createItemTest();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트상품상세설명" + "%"))
                .orderBy(qItem.price.desc())
                ;

        List<Item> itemList = query.fetch();

        itemList.forEach(s -> System.out.println(s.toString()));


    }
}