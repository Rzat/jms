package com.example.jms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloMessage {

    static final long serialVersionUID= -7088673886933530226L;

    private UUID id;
    private String message;
}
