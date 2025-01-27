package me.cocoblue.datagokrholidayapi.vo.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Items {
  private List<Holiday> item;
}