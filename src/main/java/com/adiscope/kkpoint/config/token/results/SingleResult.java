package com.adiscope.kkpoint.config.token.results;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResult {
    private T data;
}