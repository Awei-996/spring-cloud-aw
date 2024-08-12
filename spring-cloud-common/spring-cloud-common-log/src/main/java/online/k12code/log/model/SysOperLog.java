package online.k12code.log.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 该类需要建立到具体对应log表的模块里
 * 暂时先建立在这里
 *
 * @author msi
 */
@Data
public class SysOperLog {
    /** 请求方法 */
    private String method;

    /** 请求方式 */
    private String requestMethod;
    /** 操作人员 */
    private String operName;
    /** 请求url */
    private String operUrl;
    /** 操作地址 */
    private String operIp;
    /** 请求参数 */
    private String operParam;
    /** 返回参数 */
    private String jsonResult;
    /** 操作状态（0正常 1异常） */
    private Integer status;
    /** 错误消息 */
    private String errorMsg;
    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;
    /** 消耗时间 */
    private Long costTime;

}
