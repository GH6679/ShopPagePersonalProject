09PROJECTPLAN
=

## ▶️ 개발 동기

##### 기존 컴퓨터 부품 사이트기능이 과하게 디테일하고 복잡했기 때문에 좀더 단순화 되고 컴퓨터 부품에만 집중할수있게 만들고 싶었습니다,
##### 초보자는 과하게 디테일한 사이트를 이용하기위해 사전 지식을 직접 찾아야 했기 때문에 초보자 배려가 부족하다 느껴 보기편하게 설명을 추가해 초보자도 쉽게 이용할수있게 만들고 싶었습니다.
<br/>

## ▶️ 개발 목표

##### 중고 거레나 컴퓨터 악세사리에등 컴퓨터에 조립에 꼭 필요하지 않는 요소들을 배제하고 컴퓨터 필수 부품들만 취급하고 부품 목록을 직접 견적을 맞춰볼수있게 ui를 지원합니다.
##### 초보자들이 부품의 용어들 (세대,i5,i7,부품에 적힌 숫자의 뜻 등)에대해 검색하지않고도 기본적인 설명을 볼수있도록 판매게시글의 제목에서 지정된 키워드들을 검색해 해당될경우 해당 키워드의 설명을 보여주는 기능을 만듭니다. 또한 언제든지 키워드와 설명을 추가하고 삭제하고 수정할수있는 관리자 CRUD UI를 만듭니다.
##### 빠르고 쉽게 정보를 교환할수있도록 사이트 자체 게시판을 만들것이고 좋아요/싫어요 기능으로 답변률을 높입니다.
<br/>

## ▶️ 개발 일정
#### 2023-10-01 ~ 2023-10-05(05Day) : 요구사항분석 / 유스케이스 / 
#### 2023-10-05 ~ 2023-10-15(10Day) : 스타일가이드 / 스토리보드 / ERD / ClassDiagram / Sequence Diagram
#### 2023-10-16 ~ 2023-10-18(02Day) : 개발환경 구축(Github / Git / STS / Mysql / AWS ...)


<br/>

## ▶️ 구성인원 

##### 윤광혁(조장) : BackEnd (기획,키워드 검색 및 검색 CRUD)
##### 이재희(조원) : BackEnd (게시판 CRUD)
##### 이종현(조원) : BackEnd/frontend (BackEnd보조 및 메인 페이지디자인)
##### 남대희(조원2 : frontend (게시판 페이지 디자인)
##### 이은지(조원) : frontend (상품 페이지 디자인)
<br/>

## ▶️ 개발 환경(플랫폼)

##### OS : WINDOW11
##### SERVER : TOMCAT 9.0
##### CPU SPEC : Intel(R) i7-12650H
##### GPU SPEC : RTX3060m
##### RAM SPEC : 16GB SAMSUNG DDR5
##### DISK SPEC : 500GB SSD 

<br/>

## ▶️ IDE 종류

##### IntelliJ IDEA 2023-06

<br/>

## ▶️ Software 목록

##### IDE : IntelliJ IDEA 2023-06
##### SpringBoot 2.7.15
##### maven version 3.1.2
##### Git 3.1.1
##### Mysql Server 8.0
##### Mysql Workbench 8.0

<br/>

## ▶️ DevOps 

##### Amazon Web Service EC2(Deploy Server)

<br/>



## ▶️ 사용(or 예정) API

##### KAKAO login API
##### KAKAO 결제 API
##### NAVER login API
##### Google login API
##### OAuth2 login API

<br/>

## ▶️ 기술스택

![JAVA](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)


[참고 배지 싸이트] <br/>
https://badgen.net/ <br/>
https://shields.io/


<br/>

## ▶️ END POINT 

|END POINT|METHOD|DESCRIPTION|
|------|---|---|
|/product/list|GET|상품 목록표시|
|/product/set|POST|상품 등록|
|/product/keyword|POST|상품 설명용 키워드 등록|
<br/>







