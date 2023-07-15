package com.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.domain.Item;

//JpaRepository<엔티티타입, 자료형>
// - ItemRepository > 이름은 자유
// 엔터티명 + Repository > Item이라는 테이블을 전담하는 전담 객체이다. 

public interface ItemRepository extends JpaRepository<Item, String>{ // 두번쨰 타입은 pk의 자료형을 적는다.
	
	//추상 메소드
	// 1. JpaRepository > 상속메소드 > 기본 
	// 2. 사용자 정의 메소드 > 확장
	
	// 여태까진 상속받아서 구현하지 않고 썼다.
	// 근데 아래처럼 내가 구현정의를 만들면 어디가서 구현하나? 
	// 결론은 구현을 안한다. 
	
	//Optional<Item> findById(String id)
	
	// 이 3개는 생략한다. 
	Item findByName(String name); 
	Item findByNameIs(String name);
	Item findByNameEquals(String name);
	
	// 이름을 이렇게 만들면 합의가 된 것이다. 
	Item findByPrice(int price); 
	Item findByColorAndOwner(String color, String owner); 
	Item findByColorAndOwnerAndPrice(String color, String owner, int price);
	Item findByColorOrPrice(String color, int price);
	
	List<Item> findByColor(String color);
	// org.springframework.data.domain.Sort; 이다.
	List<Item> findByColor(String color, Sort sort);
	
	List<Item> findByColorAndPrice(String color, int price);
	
	List<Item> findByColorOrOwner(String color, String owner);
	
	List<Item> findByNameLike(String word);
	
	List<Item> findByNameEndingWith(String word);
	
	List<Item> findByOwnerNull();
	
	//List<Item> findByOwnerEmpty();
	
	List<Item> findByOwnerNotNull();
	
	List<Item> findAllByOrderByColor();
	
	List<Item> findAllByOrderByColorDesc();
	
	List<Item> findByOwnerOrderByColorDesc(String string);
	
	List<Item> findByPriceGreaterThan(int i);
	
	List<Item> findByPriceGreaterThan(int i, Sort by);
	
	List<Item> findByPriceLessThan(int i, Sort by);
	
	List<Item> findByPriceBetween(int i, int j);
	
	List<Item> findByOrderdateBetween(String string, String string2);
	
	List<Item> findByColorIgnoreCase(String string);
	
	List<Item> findByColorIn(List<String> colors);
	
	List<Item> findByOwnerIn(String[] strings);
	
	List<Item> findByOwnerNotIn(String[] strings);
	
	Item findFirstByOrderByPriceAsc();
	
	Item findTopByOrderByPriceAsc();
	
	List<Item> findTop3ByOrderByPriceDesc();
	
	
	List<Item> findPageListBy(PageRequest pageRequest);
	
	
	// 이렇게 붙여주면 된다. 쿼리가 길고 복잡하면 이방법으로 쓴다. 
	@Query(value="select * from Item", nativeQuery=true)
	
	List<Item> findAllItem();
	
	// 이 쿼리를 JPQL 이라고 한다 > Java Persistence Language 
	@Query(value="select * from Item where color = :color", nativeQuery=true)
	List<Item> findAllItemByColor(String color); // 생략한것도 된다.
	// List<Item> findAllItemByColor(@Param("color") String aaa); // 이거도 되고 
	
	
	
}


