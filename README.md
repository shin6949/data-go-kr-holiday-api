# 공공데이터 특일 API 사용 예제
이 예제는 한국천문연구소에서 공공데이터 특일 API를 사용하는 예제입니다.

## Environment
- Java 17
- Gradle 8.5
- Spring Boot 3.3.2
- Spring WebFlux
- Lombok

## Feature
- 특일 API를 사용하여 특정 월의 특일 정보를 조회합니다.
- 특일 API Json 형태의 응답에 대응합니다.
- Json 형태의 응답에서 특일 개수에 따라 달라지는 형태에 대응하여 Deserializer 를 구성했습니다.

## See More Description On
- [한국천문연구소 공공데이터 특일 API](https://data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15012690)
- [Author Blog](https://blog.cocoblue.me/issue-of-using-holiday-api-at-data-go-kr/)