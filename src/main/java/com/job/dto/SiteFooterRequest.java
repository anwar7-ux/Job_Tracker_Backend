package com.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteFooterRequest {

    private String aboutText;
    private String copyrightText;
    private String contactEmail;
    private String className;
}
