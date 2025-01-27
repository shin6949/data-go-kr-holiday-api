package me.cocoblue.datagokrholidayapi.vo.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Body {
  @JsonDeserialize(using = ItemsDeserializer.class)
  private Items items;
  private int numOfRows;
  private int pageNo;
  private int totalCount;
}