package online.k12code.file.config;

import lombok.extern.slf4j.Slf4j;
import online.k12code.file.properties.FileProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link WebMvcConfigurationSupport} 优先级比 {@link WebMvcConfigurer} 高
 * <p>
 * 使用了 {@link WebMvcConfigurationSupport} 之后，{@link WebMvcConfigurer} 会失效
 * @author msi
 */
@Slf4j
@Configuration
public class WebMvcConfigurationSupportConfig extends WebMvcConfigurationSupport {

    private FileProperties fileProperties;

    @Autowired
    public void setFileProperties(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 重写addResourceHandlers 如果resources目录下有静态文件就必须要添加一下这行处理，否则资源不会加载
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

        List<FileProperties.ResourceHandler> resourceHandlers = fileProperties.getResourceHandlers();

//        String localFilePrefix = fileProperties.getLocalFilePrefix();
//        String localUrlPrefix = fileProperties.getLocalUrlPrefix();

//        ArrayList<FileProperties.ResourceHandler> handlerArrayList = new ArrayList<>(resourceHandlers);
//        if (StringUtils.hasText(localFilePrefix)&&(StringUtils.hasText(localUrlPrefix))) {
//            FileProperties.ResourceHandler resourceHandler = new FileProperties.ResourceHandler();
//            resourceHandler.setAddResourceHandler(localUrlPrefix);
//            resourceHandler.setAddResourceLocations(localFilePrefix);
//            handlerArrayList.add(resourceHandler);
//        }

        if (CollectionUtils.isEmpty(resourceHandlers)) {
            log.warn("本地静态资源配置为空");
        } else {
            for (FileProperties.ResourceHandler resourceHandler : resourceHandlers) {
                String addResourceHandler = resourceHandler.getAddResourceHandler();
                String addResourceLocations = resourceHandler.getAddResourceLocations();

//                if (!addResourceHandler.endsWith("/**")) {
//                addResourceHandler = UriComponentsBuilder.newInstance()
//                                    .path(addResourceHandler)
//                                    .path("/**")
//                                    .toUriString();
//                }
//
//                if (!addResourceLocations.startsWith("file:")) {
//                    addResourceLocations = "file:" +addResourceLocations;
//                }
//
//                if (!addResourceHandler.endsWith("/")) {
//                    addResourceLocations += "/";
//                }

                log.info("网络路径：{},映射到本地的路径：{}",addResourceHandler,addResourceLocations);
                File directory = new File(addResourceLocations.replaceFirst("file:", ""));
                if (directory.exists()) {
                    log.info("文件夹：{} 已存在",directory);
                } else {
                    boolean mkdir = directory.mkdir();
                    log.info("文件夹：{}，创建结果：{}",directory,mkdir);
                }
                // addResourceHandler(添加访问的路径).addResourceLocations(对应本地的路径)
                registry.addResourceHandler(addResourceHandler).addResourceLocations(addResourceLocations);
            }
        }
    }
}
