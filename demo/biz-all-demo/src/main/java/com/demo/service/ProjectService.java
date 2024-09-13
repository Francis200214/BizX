package com.demo.service;

import com.biz.operation.log.OperationLog;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @OperationLog(category = "PROJECT_OPERATION", subcategory = "CREATE_PROJECT",
            content = "'New project created: ' + #project.name + ', Start Date: ' + #project.startDate + ', End Date: ' + #project.endDate")
    public void createProject(Project project) {

    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Project {
        private String name;
        private String startDate;
        private String endDate;
    }
}
