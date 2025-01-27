package me.cocoblue.datagokrholidayapi.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.cocoblue.datagokrholidayapi.vo.api.Response;

/**
 * 공공데이터포털의 '한국 공휴일 정보' API 응답을 담는 VO 클래스
 *
 * @see <a href="https://www.data.go.kr/data/15012690/openapi.do">한국 공휴일 정보 API</a>
 * @author shin6949
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class HolidayApiResponseVo {
    private Response response;
}