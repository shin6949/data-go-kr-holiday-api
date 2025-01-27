package me.cocoblue.datagokrholidayapi.vo.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Holiday {
  private String dateKind;
  private String dateName;
  private String isHoliday;
  private int locdate;
  private int seq;
}