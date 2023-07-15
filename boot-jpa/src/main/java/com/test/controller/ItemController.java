package com.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import com.test.domain.Item;
import com.test.repository.ItemRepository;

@Controller
public class ItemController {
	
	//CRUD
	// - JPA Repository 만들어야 한다.
	@Autowired
	private ItemRepository itemRepo;
	// Field itemRepo in com.test.controller.ItemController required a bean of type 'com.test.repository.ItemRepository' that could not be found.
	// 아직 처음에는 스캔 대상이 아니다. 그래서 얘도 스캔해야 한다.
	
	@GetMapping("/item/m1")
	public String m1(Model model) {
		
		System.out.println("m1");
		
		//[C]RUD
		// - 레코드 추가하기 
		
		Item item = new Item();
		item.setName("잉크젯 프린터");
		item.setPrice(250000);
		item.setColor("yellow");
		item.setOwner("홍길동");
		
		// save > (JPA) > insert
		Item result = itemRepo.save(item);
		// Hibernate: insert into item (color, orderdate, owner, price, name) values (?, ?, ?, ?, ?)
		// orderdate date default sysdate not null > db엔 이렇게 되어 있다. 
		
		model.addAttribute("result", result);
		
		return "item/result";
	}
	
//	@GetMapping("/item/m1")
//	public String m1(Model model) {
//		return "item/result";
//	}
	
	@GetMapping("/item/m2")
	public String m2(Model model) {
		
		System.out.println("m2");
		
		//[C]RUD
		// - 레코드 추가하기 
		
		//빌더 패턴(Builder Pattern)
		// 생성자 패턴, 빌더 패턴 
		// - OOP에서 겍체를 생성하는 패턴 중 하나
		Item item2 = new Item(null, 0, null, null, null);
		
		
		// 얘는 멤버를 자유롷게 넣을 수 있어서 필요없으면 뺴고 안넣어도 된다. 가독성있다.
		Item item = Item.builder()
				.name("레이저 프린터")
				.price(300000)
				.color("black")
				.owner("아무개")
				.build();

		
		// save > (JPA) > insert
		Item result = itemRepo.save(item);
		// Hibernate: insert into item (color, orderdate, owner, price, name) values (?, ?, ?, ?, ?)
		// orderdate date default sysdate not null > db엔 이렇게 되어 있다. 
		
		model.addAttribute("result", result);
		
		return "item/result";
	}
	
	@GetMapping("/item/m3")
	public String m3(Model model) {
		//C[R]UD
		// - 단일 레코드 읽기
		
		
//		select item0_.name as name1_0_0_, item0_.color as color2_0_0_, item0_.orderdate as orderdate3_0_0_, item0_.owner as owner4_0_0_, item0_.price as price5_0_0_ from item item0_ where item0_.name=?
//				Hibernate: select item0_.name as name1_0_0_, item0_.color as color2_0_0_, item0_.orderdate as orderdate3_0
		// 내무적으로 쿼리로 동작한다. 메소드사 더이상 안쓰인다. 
		
		
		
		//Item item = itemRepo.getById("마우스"); // 얘는 더이상 안쓰임
		//model.addAttribute("result", item);
		Optional<Item> item = itemRepo.findById("프린터"); // ★ 얘는 가장 많이 쓰인다. 레코드 찾을 때
		System.out.println(item.isPresent());
		model.addAttribute("result", item.get());
		return "item/result";
	}
	
	@GetMapping("/item/m4")
	public String m4(Model model) {
		//업데이트 
//		Item item = Item.builder()
//					.name("프린터") // 조건절
//					.price(230000) 
//					.color("white")
//					.owner("홍길동")
//					.build();
		// 수정에 관련한 메서드가 없다. 
//		Item result = itemRepo.save(item); // 이게 수정이다.
		// 업데이트가 되는데 셀렉트도 되어있다. 이걸
		// insert 해야하는지 업데이트 해야하는지 판단하려고 있는 것 
	
		Optional<Item> item = itemRepo.findById("프린터");
		
		if(item.isPresent()) {
			item.get().setPrice(240000);
			Item result = itemRepo.save(item.get());
			model.addAttribute("result", result);
		}
		
		
		return "item/result";
	}
	
	
	@GetMapping("/item/m5")
	public String m5(Model model) {
		//CRU[D]
		Optional<Item> item = itemRepo.findById("노트북");
		itemRepo.delete(item.get());
		
		return "item/result";
	}
	
	@GetMapping("/item/m6")
	public String m6(Model model) {
		
		//Query Methods
		//Repository에 구현된 메서드가 쿼리 메서드다.
		// 다중 레코드 조회
		List<Item> list = itemRepo.findAll();
		model.addAttribute("list", list);
		
		return "item/result";
	}
	
	@GetMapping("/item/m7")
	public String m7(Model model) {
		// 존재 유무 확인
		boolean bool = itemRepo.existsById("마우스");
		model.addAttribute("bool", bool);
		return "item/result";
	}
	
	@GetMapping("/item/m8")
	public String m8(Model model) {
		
		// 카운트
		long count = itemRepo.count();
		
		model.addAttribute("count", count);
		
		return "item/result";
	}
	
	@GetMapping("/item/m9")
	public String m9(Model model) {
		//findByName으로 자동으로 구현 
		
		// Item item = itemRepo.findByName("마우스");
		// Item item = itemRepo.findByPrice(100000);
		// 메서드 이름 패턴이 중요하다. 
		// 가장 중요하게 생각하는 패턴이 > findBy이다. 
		
		// findBy컬럼명
		// - By : Po
		//Item item = itemRepo.findByNameIs("키보드");
		Item item = itemRepo.findByNameEquals("키보드");
		
		model.addAttribute("result", item);
		return "item/result";
	}
	
	@GetMapping("/item/m10")
	public String m10(Model model) {
		
		// And, Or
		//Item item = itemRepo.findByColorAndOwner("yellow", "홍길동");
		//Item item = itemRepo.findByColorAndOwnerAndPrice("black", "홍길동", 150000);
		
		Item item = itemRepo.findByColorOrPrice("gold", 300000);
		//where item0_.color=? and item0_.owner=? and item0_.price=?
		
		model.addAttribute("result", item);
		return "item/result";
	}
	
	@GetMapping("/item/m11")
	public String m11(Model model) {
		
		//List<Item> list = itemRepo.findByColor("white");
		
		//List<Item> list= itemRepo.findByColor("white", Sort.by(Sort.Direction.ASC, "price"));
		
		//color를 조건 price를 기준으로 order by를 했다.
		
		//List<Item> list= itemRepo.findByColorAndPrice("yellow", 95000);
		
		//List<Item> list= itemRepo.findByColorOrOwner("white", "홍길동");
		
		//- findByNameLike()
		//- findByNameIsLike()
		
		//- findByNameNotLike()  // 이거 빼고 찾아줘 ~ 
		//- findByNameIsNotLike()
				
		
		//- %, _를 직접 명시 
		//List<Item> list= itemRepo.findByNameLike("키보드");
		//List<Item> list= itemRepo.findByNameLike("%키보드");
		
		// - findByName[Is]StartingWith
		// - findByName[Is]EndingWith
		// - findByName[Is]Containing
		
		List<Item> list= itemRepo.findByNameEndingWith("마우스");
		
		model.addAttribute("list", list);
		
		return "item/result";
	}
		
	@GetMapping("/item/m12")
	public String m12(Model model) {
		// [Is]Null, [Is]NotNull
		// - 컬럼값이 null인 레코드 검색 
		
		// [Is]Empty, [Is]NotEmpty
		// - 컬럼값이 null이거나 빈문자열인 레코드 검색
		
		//List<Item> list = itemRepo.findByOwnerNull();
		//List<Item> list = itemRepo.findByOwnerEmpty();
		//List<Item> list = itemRepo.findByOwnerNotNull();
		
		// 정렬 
		// List<Item> list = itemRepo.findAll(Sort.by(Sort.Direction.DESC, "price")); // > findAll
		
		// List<Item> list = itemRepo.findAll(Sort.by("name")); // 오름차순
		
		// List<Item> list = itemRepo.findAllByOrderByColor();
		
		// List<Item> list = itemRepo.findAllByOrderByColorDesc();
		
		// List<Item> list = itemRepo.findByOwnerOrderByColorDesc("홍길동");
		
		//	where item0_.owner=? order by item0_.color desc
		
		
		// [IS]GreaterThan, [IS]LessThan, [Is]Between
		// [Is]GreaterThanEqual, [Is]LessThanEqual
		
		// List<Item> list = itemRepo.findByPriceGreaterThan(100000);
		
		// List<Item> list = itemRepo.findByPriceGreaterThan(100000, Sort.by("price"));
		
		//List<Item> list = itemRepo.findByPriceLessThan(100000, Sort.by("price"));
		
		// List<Item> list = itemRepo.findByPriceBetween(90000, 120000);
		
		// List<Item> list = itemRepo.findByOrderdateBetween("2023-06-25", "2023-06-27");
		
		//IgnoreCase
		//- 특정 컬럼의 대소문자를 구분하지 않고 검색
		// List<Item> list = itemRepo.findByColor("white");
		
		// List<Item> list = itemRepo.findByColorIgnoreCase("White");
		
		
		// In, NotIn
		// - Where color in ('yellow', 'blue')
		
//		List<String> colors = new ArrayList<String>();
//		colors.add("yellow");
//		colors.add("blue");
//		
//		List<Item> list = itemRepo.findByColorIn(colors);
		
		//List<Item> list = itemRepo.findByOwnerIn(new String[]{"홍길동", "아무개"});
		
		List<Item> list = itemRepo.findByOwnerNotIn(new String[]{"홍길동", "아무개"});
		 
		model.addAttribute("list", list);
		
		return "item/result";
	}
	
	//@RequestParam("name") 이거 원래 생략했는데 생략안하고 쓰고 
	@GetMapping("/item/m13")
	public String m13(Model model, @RequestParam("name") Item result) {
		
		// PK를 리퀘스트 했을 뿐인데 그걸 베이스로 쿼리를 만들어서 아이템에 넣어주는 작업도 해주는 것이다. 
		// 도메인 클래스 컨버터(Domain Class Converter)
		// - PK를 넘겨서, 바로 Entity를 조회할 수 있다. 
		
		System.out.println(result);
		model.addAttribute("result", result);
		
		// item/m13?name=키보드
		
		//"마우스" > PK  
		// Optional<Item> result = itemRepo.findById(name);
		// model.addAttribute("result", result.get()); // Optional은 꺼내야 한다.
		
		
		return "item/result";
	}
	
	// 이 떄는 @PathVariable 이나 @RequestParam를 생략하면 안된다. 
	@GetMapping("/item/m14/{name}")
	public String m14(Model model, @PathVariable("name") Item result) {
		
		//item/m13?name=마우스
		//item/m13/마우스
		model.addAttribute("result", result);
		
		return "item/result";
	}
	
	@GetMapping("/item/m15")
	public String m15(Model model) {
		
		// First
		// Top
		// 둘다 첫번쨰거 가져오는데 First는 무조건하나를 가져오는데 ★ Top은 원하는 범위를 선택한다.
		
		// Item result = itemRepo.findFirstByOrderByPriceAsc();
		// Item result = itemRepo.findTopByOrderByPriceAsc();
		
		List<Item> list = itemRepo.findTop3ByOrderByPriceDesc();
		
		model.addAttribute("list", list);
		
		return "item/result";
	}
	
	@GetMapping("/item/m16")
	public String m16(Model model, int page) {
		
		// PageRequest 이게 페이징을 담당한다. 
		PageRequest pageRequest = PageRequest.of(page, 5, Sort.by("name"));
		// Page는 0부터다 !!! 
		List<Item> list = itemRepo.findPageListBy(pageRequest);
		model.addAttribute("list", list);
		
		return "item/result";
	}
	
	@GetMapping("/item/m17")
	public String m17(Model model) {
		
		//@Query 
		// - 사용자 쿼리 작성
		// - 쿼리 메소드 키워드로 작성 불가능 쿼리 > 직접 SQL 작성 
		
		// select * from Item 
		
		List<Item> list = itemRepo.findAllItem(); // 메서드 이름이 중요하지 않다. 쿼리를 내맘대로 짠다. 쿼리를 내가 만든다. 
		
		model.addAttribute("list", list);
		
		return "item/result";
	}
	
	@GetMapping("/item/m18")
	public String m18(Model model, String color) {
		
		List<Item> list = itemRepo.findAllItemByColor(color);
		
		model.addAttribute("list",list);
		
		return "item/result";
	}
}
