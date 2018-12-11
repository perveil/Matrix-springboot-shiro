package org.tustcs.shiro.pojo.enums;

/**
 * @author Lin
 */

public enum StatusEnum {
                        // http返回碼+狀態分析
                        ERR_JWT(601,
                                "error jwt"), NO_PERMISSION(602,
                                                            "no permission"), NEW_JWT(603,
                                                                                      "new jwt"), NO_JWT(604,
                                                                                                         "no jwt"), REFRESHED_JWT(605,
                                                                                                                                  "jwt refreshed"), LOGIN_ERROR(701,
                                                                                                                                                                "invalid username or password"), USER_NULL(702,
                                                                                                                                                                                                           "user not exist"), USER_EXIST(703,
                                                                                                                                                                                                                                         "user exists"), USER_FREEZE(704,
                                                                                                                                                                                                                                                                     "user freeze"), SRC_NOTFOUND(801,
                                                                                                                                                                                                                                                                                                  "resource not found"), SRC_NULL(802,
                                                                                                                                                                                                                                                                                                                                  "resource not exist"), SRC_EXIST(803,
                                                                                                                                                                                                                                                                                                                                                                   "resource exists"), ROLE_NOTFOUND(901,
                                                                                                                                                                                                                                                                                                                                                                                                     "role not found"), ROLE_NULL(902,
                                                                                                                                                                                                                                                                                                                                                                                                                                  "role not exist"), ROLE_EXIST(903,
                                                                                                                                                                                                                                                                                                                                                                                                                                                                "role exists"),;

    private Integer code;

    private String  msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
