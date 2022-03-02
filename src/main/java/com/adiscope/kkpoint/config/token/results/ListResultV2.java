package com.adiscope.kkpoint.config.token.results;

import com.adiscope.kkpoint.config.token.enums.ListTypeEnums;
import lombok.Data;

import java.util.List;

@Data
public class ListResultV2<T> extends CommonResult {

    private ListData<T> data;

    @Data
    public static class ListData<T> {
        private List<T> list;
        private ListTypeEnums listType;
    }
}