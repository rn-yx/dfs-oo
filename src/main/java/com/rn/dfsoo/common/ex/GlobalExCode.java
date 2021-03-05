package com.rn.dfsoo.common.ex;

/**
 * 描述：全局编码枚举类
 * ---------------------------------------------------------
 * 编码定义格式说明：
 * 1.基础网络异常保持通用,编号1000以下；
 * 2.通用异常（4位）：前二位（模块）+ 后两位（序号）
 * 3.业务异常（5位）：前三位（模块）+ 后两位（序号）
 * ----------------------------------------------------------
 *
 * @author 然诺
 * @date 2019/9/26
 */
public enum GlobalExCode implements IExceptionCode {
    // 系统相关
    SYSTEM_BUSY(-1, "系统繁忙，请稍候再试"),
    SYSTEM_TIMEOUT(-2, "系统超时，请稍候再试"),
    REQUEST_REPEAT(-3, "请勿重复提交"),
    TOKEN_EXPIRED(-4, "token已过期"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // 网络交互相关
    BAD_REQUEST(400, "请求的参数个数或格式不符合要求"),
    INVALID_ARGUMENT(400, "请求的参数不正确"),
    UNAUTHORIZED(401, "没有权限"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "请求的地址错误"),
    METHOD_NOT_ALLOWED(405, "不允许的请求方法"),
    NOT_ACCEPTABLE(406, "不接受的请求"),
    CONFLICT(409, "资源冲突"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的Media Type"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    REQUEST_TIMEOUT(504, "请求服务超时"),
    ENCODING_ERROR(600, "编码错误"),
    METHOD_ARGUMENT_TYPE_MISMATCH(601, "请求参数类型错误"),
    METHOD_ARGUMENT_COUNT_MISMATCH(602, "请求参数缺失错误"),
    METHOD_ARGUMENT_BIND_EX(603, "请求参数有误，绑定异常"),
    METHOD_REQUEST_PART_MISSING(604, "所需的请求参数缺失数据"),

    // 数据库相关
    DB_ADD_FAIL(1000, "保存数据失败"),
    DB_REMOVE_FAIL(1001, "软删除失败"),
    DB_DELETE_FAIL(1002, "删除失败"),
    DB_UPDATE_FAIL(1003, "更新失败"),
    DB_QUERY_FAIL(1004, "查询失败"),
    DB_DUPLICATE_KEY_EXCEPTION(1005, "DB重复键（索引）异常"),
    DB_DATA_INTEGRITY_VIOLATION_EX(1006, "数据完整性违规异常（可能是字段缺少或错误）"),

    // 操作相关
    ACTION_FAILURE(1100, "操作失败"),
    CALLING_REMOTE_SERVICE_FAIL(1101, "远程服务调用失败"),
    ACCESS_SOURCE_NOT_EXIST(1102, "请求访问资源不存在"),
    ILLEGAL_OPERATE(1103, "非法操作"),
    UPLOAD_FILE_TOO_LARGE(1104, "上传文件过大"),

    FILE_SIZE_EXCEEDED_MAX_LIMIT(1104, "上传文件的大小超出限制"),
    FILE_WRITE_FAIL(1105, "写入文件失败"),
    FILE_MOVE_FAIL(1106, "移动临时文件失败"),
    FILE_HASH_WRONG(1107, "文件HASH计算有误"),
    FILE_DELETE_FAIL(1108, "文件删除失败"),
    FILE_TYPE_WRONG(1109, "文件类型错误"),
    FILE_IS_EMPTY(1110, "文件为空"),
    FILE_META_SAVE_FAIL(1111, "保存文件元数据失败"),

    ;

    private int status;
    private String message;

    GlobalExCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static GlobalExCode fromStatus(int httpStatus) {
        for (GlobalExCode errorCode : values()) {
            if (errorCode.getStatus() == httpStatus) {
                return errorCode;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }

    public static GlobalExCode fromMessage(String message) {
        if (message != null) {
            for (GlobalExCode errorCode : values()) {
                if (message.equals(errorCode.getMessage())) {
                    return errorCode;
                }
            }
        }
        return INTERNAL_SERVER_ERROR;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return this.name();
    }

}
