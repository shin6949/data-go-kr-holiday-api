package me.cocoblue.datagokrholidayapi.vo.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemsDeserializer extends StdDeserializer<Items> {

  public ItemsDeserializer() {
    super(Items.class);
  }

  @Override
  public Items deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    // 1) JSON 노드 얻기
    JsonNode node = p.getCodec().readTree(p);

    // 2) 결과로 반환할 Items 객체 생성
    Items items = new Items();

    // 3) Items 안의 List<Holiday>를 채워 넣을 임시 List<Holiday>
    List<Holiday> holidayList = new ArrayList<>();

    // [Case A] items가 ""(빈 문자열)인 경우
    if (node.isTextual() && node.asText().isEmpty()) {
      // holidayList는 빈 상태 그대로 사용
      items.setItem(holidayList);
      return items;
    }

    // [Case B] items가 객체 {}인 경우
    if (node.isObject()) {
      // 내부에 "item" 노드가 있는지 확인
      JsonNode itemNode = node.get("item");
      if (itemNode != null) {
        // itemNode가 단일 객체 {}
        if (itemNode.isObject()) {
          Holiday oneHoliday = p.getCodec().treeToValue(itemNode, Holiday.class);
          holidayList.add(oneHoliday);
        }
        // itemNode가 배열 []
        else if (itemNode.isArray()) {
          for (JsonNode arrNode : itemNode) {
            Holiday holiday = p.getCodec().treeToValue(arrNode, Holiday.class);
            holidayList.add(holiday);
          }
        }
        // 그 외 문자열 등 예외 케이스는 일단 빈 리스트 처리
      }
      // itemNode == null일 때도 빈 리스트
      items.setItem(holidayList);
      return items;
    }

    // [Case C] 그 외 (배열 등) -> API 스펙상 거의 없겠지만 방어적으로 처리
    // 혹시나 items 필드가 배열로 오는 경우. (일반적이지 않지만)
    if (node.isArray()) {
      // 그래도 안에 "item"이 들어있을 수 있으니 시도
      for (JsonNode arrNode : node) {
        // 만약 arrNode 자체가 Holiday의 필드를 포함한다면 파싱
        Holiday holiday = p.getCodec().treeToValue(arrNode, Holiday.class);
        holidayList.add(holiday);
      }
      items.setItem(holidayList);
      return items;
    }

    // 여기까지 왔으면 알 수 없는 형태
    items.setItem(holidayList);
    return items;
  }
}