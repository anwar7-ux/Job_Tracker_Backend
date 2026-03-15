package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FooterNavigationRequest {
    private String navigationName;
    private String navigationSrc;
    private String className;
}
