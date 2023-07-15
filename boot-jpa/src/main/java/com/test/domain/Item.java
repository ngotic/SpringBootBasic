package com.test.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA에서는 이걸 Entity라고 한다. 
// - 데이터베이스의 테이블 <- (연결) -> JPA의 엔티티 
// - 매핑되는 테이블명과 엔티티의 이름을 동일하게 만든다. 

// 이런 Item이면 나중에 tblItem으로 추가로 붙일 수 있다. 

// @Entity(name="tblItem") // 이렇게 관리가 가능하다.

// @Entity 는 jpa의 기능이다. 


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
	// 엔티티가 참조하는 테이블의 컬럼 > 멤버 정의
	// PK는 꼭 붙여야 한다. 
	@Id
	private String name;
	
	private int price;
	private String color;
	private String owner;
	
	@Column(insertable=false, updatable=false) // 얘를 업데이트하는 대상에서 뺴버린다.
	private String orderdate; // 이 컬럼은 insert를 안해도 된다. default가 있다. 
	
}













