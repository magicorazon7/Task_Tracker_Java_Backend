package com.tracker.dto;

import com.tracker.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatCount {
    private TaskStatus status;
    private Long count;
}
