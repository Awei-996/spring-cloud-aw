package online.k12code.log.service;

import online.k12code.log.model.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步存储日志 Feign调用
 *
 * @author msi
 */
@Service
public class AsyncLogService {

//    private final RemoteLogService remoteLogService;
//
//    public AsyncLogService(RemoteLogService remoteLogService) {
//        this.remoteLogService = remoteLogService;
//    }

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveLog(SysOperLog sysOperLog) throws Exception {
//        remoteLogService.saveLog(sysOperLog, SecurityConstants.INNER);
    }
}
