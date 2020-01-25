package com.example.callsservice.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Call {

    public enum Type {
        INBOUND,
        OUTBOUND
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, length = 9, name = "caller")
    private String callerNumber;
    @Column(nullable = false, length = 9, name = "callee")
    private String calleeNumber;
    @Column(nullable = false, name = "start")
    private Timestamp startTimestamp;
    @Column(nullable = false, name = "end")
    private Timestamp endTimestamp;
    @Column(nullable = false, name = "type")
    private Type type;
}
