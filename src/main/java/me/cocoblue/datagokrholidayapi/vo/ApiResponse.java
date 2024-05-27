package me.cocoblue.datagokrholidayapi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import java.util.List;

@Data
public class ApiResponse {
    @JsonProperty("header")
    private Header header;

    @JsonProperty("body")
    private Body body;

    @Data
    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Data
    public static class Body {
        @JsonProperty("items")
        private Items items;

        @JsonProperty("numOfRows")
        private int numOfRows;

        @JsonProperty("pageNo")
        private int pageNo;

        @JsonProperty("totalCount")
        private int totalCount;
    }

    @Data
    public static class Items {
        @JsonProperty("item")
        private List<Item> item;
    }

    @Data
    public static class Item {
        @JsonProperty("dateKind")
        private String dateKind;

        @JsonProperty("dateName")
        private String dateName;

        @JsonProperty("isHoliday")
        private String isHoliday;

        @JsonProperty("locdate")
        private int locdate;

        @JsonProperty("seq")
        private int seq;
    }
}
