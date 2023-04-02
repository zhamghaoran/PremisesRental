package test;

import com.rental.premisesrental.mapper.UserMapper;
import com.rental.premisesrental.service.ScheduledTasks;
import com.rental.premisesrental.service.impl.ScheduledTasksImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.auditing.ReactiveIsNewAwareAuditingHandler;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@SpringBootTest(classes = com.rental.premisesrental.PremisesRentalApplication.class)
public class Test1 {

    @Autowired
    private ScheduledTasks scheduledTasks;

    @Test
    public void testTask() {
        scheduledTasks.databaseTask();
    }

}
