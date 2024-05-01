package com.pm.values;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ResponseData {
    private final static Integer DONE = 200;
    private final static Integer ERROR = 400;

    private Integer code;
    private String msg;
    private Object result;

    public Map<String, Object> resultMap() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        resultMap.put("data", result);

        return resultMap;
    }

    @Builder
    public ResponseData(Boolean isSuccess, String msg, Object result) {
        this.code = isSuccess ? this.DONE : this.ERROR;
        this.msg = msg;
        this.result = result;
    }
}