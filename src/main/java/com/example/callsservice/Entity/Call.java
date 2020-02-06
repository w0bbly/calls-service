package com.example.callsservice.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Table
@Entity(name = "call")
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(nullable = false)
    private Date startTime;
    @Column(nullable = false)
    private Date endTime;
    @Column(nullable = false, name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
    @Transient
    private Double totalPrice;
}
