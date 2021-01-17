package com.deepblue.aspect;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    private String username;
    private String password;
}
