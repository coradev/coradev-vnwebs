package com.coradev.vnwebs.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostQuery {
    private String title;
    private Long categoryId;
    private boolean recommend;
}
