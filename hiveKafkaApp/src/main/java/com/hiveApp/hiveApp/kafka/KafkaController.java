package com.hiveApp.hiveApp.kafka;



import com.hiveApp.hiveApp.kafka.bo.TaskBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("kafka")
public class KafkaController {

    @Autowired
    KafkaTemplate<String, TaskBO> stringTaskBOKafkaTemplate;

    public static final String TOPIC_TASK = "kafka_tasks";

    @GetMapping("/publish/json/{name}")
    public String postJson(@PathVariable("name") final String name) {

        TaskBO taskBO = new TaskBO(name,new Date());
        stringTaskBOKafkaTemplate.send(TOPIC_TASK, taskBO);

        return "Published successfully";
    }

}
