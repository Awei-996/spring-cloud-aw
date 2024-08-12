package online.k12code.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author msi
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("aw.cloud.file")
public class FileProperties {

    private List<ResourceHandler> resourceHandlers;

    /**
     * 本地存储上传文件 域名
     */
    private String localDomainName;

    /**
     * 本地存储：上传文件后 返回的url 的前缀
     */
    private String localUrlPrefix;

    /**
     * 本地存储：上传文件后 本地存储 前缀，不能为空
     */
    private String localFilePrefix;

    /**
     * 本地存储静态资源映射
     */
    @Data
    public static class ResourceHandler {
        /**
         * 网络路径
         */
        private String addResourceHandler;

        /**
         * 本地路径
         */
        private String addResourceLocations;
    }
}
