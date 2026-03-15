package com.job.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusHistoryResponse {
    private long id;
    private String oldStatus;
    private String newStatus;
    private LocalDateTime changedAt;
}
