package org.tustcs.shiro.pojo;

import lombok.Data;
import org.tustcs.shiro.pojo.enums.StatusEnum;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class ResponseBean {

    private Map<String, Object> meta = new HashMap<>();

    private Map<String, Object> data = new HashMap<>();

    public ResponseBean() {
    }

    public ResponseBean(StatusEnum statusEnum) {
        meta.put("code", statusEnum.getCode());
        meta.put("msg", statusEnum.getMsg());
    }

    public ResponseBean addData(String key, Object object) {
        data.put(key, object);
        return this;
    }

    public ResponseBean addMeta(String key, Object object) {
        meta.put(key, object);
        return this;
    }

    public ResponseBean ok(String message) {
        meta.put("code", 200);
        meta.put("msg", message);
        return this;
    }

    public ResponseBean ok() {
        meta.put("code", 200);
        meta.put("msg", "ok");
        return this;
    }

    public ResponseBean error(int statusCode, String message) {
        meta.put("code", statusCode);
        meta.put("msg", message);
        return this;
    }

    public ResponseBean error() {
        meta.put("code", -1);
        meta.put("msg", "unknown error");
        return this;
    }

    public ResponseBean error(StatusEnum statusEnum) {
        meta.put("code", statusEnum.getCode());
        meta.put("msg", statusEnum.getMsg());
        return this;
    }

}
