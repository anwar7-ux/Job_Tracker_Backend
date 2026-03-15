package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteHeaderRequest {

    private String websiteName;
    private String logoUrl;
    private String className;
}
