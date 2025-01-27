package me.cocoblue.datagokrholidayapi.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.cocoblue.datagokrholidayapi.vo.HolidayApiResponseVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;

/**
 * 공공데이터 공휴일 API에 요청하기 위한 서비스
 *
 * @author shin6949
 * @version 1.0.0
 * @since 1.0.0
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class AnniversaryDateService {

    private WebClient webClient;

    @Value("${application.holiday-api.key:null}")
    private String holidayApiToken;

    @PostConstruct
    public void initWebClient() {
        final DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        webClient = WebClient.builder()
                .uriBuilderFactory(factory)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br")
                .build();
    }

    /**
     * 주어진 연도와 월에 대한 API에서 휴일 정보를 검색합니다.
     *
     * @param year  휴일 정보를 검색할 연도입니다. null이면 현재 연도를 사용합니다.
     * @param month 휴일 정보를 검색할 월입니다. null이면 현재 월을 사용합니다.
     * @return 휴일 정보를 포함하는 응답 엔티티를 반환합니다.
     */
    public ResponseEntity<HolidayApiResponseVo> getHolidayFromApi(Integer year, Integer month) {
        log.debug("Fetching holiday data from the API.");
        log.debug("Holiday API Token: {}", holidayApiToken);

        if(holidayApiToken == null || holidayApiToken.equals("null")) {
            log.error("Holiday API Token is not set. Please set the token in the application.yml file.");
            return null;
        }

        if (year == null) {
            year = LocalDateTime.now().getYear();
        }

        if (month == null) {
            month = LocalDateTime.now().getMonthValue();
        }

        final String yearString = String.format("%04d", year);
        final String monthString = String.format("%02d", month);

        try {
            // WebClient에 URI 전달
            final ResponseEntity<HolidayApiResponseVo> result = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("apis.data.go.kr")
                            .path("/B090041/openapi/service/SpcdeInfoService/getRestDeInfo")
                            .queryParam("_type", "json")
                            .queryParam("solYear", yearString)
                            .queryParam("solMonth", monthString)
                            .queryParam("ServiceKey", holidayApiToken)
                            .build())
                    .retrieve()
                    .toEntity(HolidayApiResponseVo.class)
                    .block();

            log.debug("Holiday API Response: {}", result);
            return result;
        } catch (Exception e) {
            log.error("Failed to fetch holiday data from the API. {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 현재 연도와 월에 대한 API에서 휴일 정보를 검색
     *
     * @return 휴일 정보를 포함하는 응답 엔티티
     */
    public ResponseEntity<HolidayApiResponseVo> getHolidayFromApiCurrentMonth() {
        return getHolidayFromApi(null, null);
    }
}
