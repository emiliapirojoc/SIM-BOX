package com.unifun.voice.orm.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="mnoList")
@Data
public class MnoListDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="name")
    String name;
    @Column(name = "simboxId")
    int simboxId;
    @Column(name = "uri")
    String uri;
    @Column(name = "wsServer")
    String wsServer;
    @Column(name = "user")
    String user;
    @Column(name = "password")
    String password;
}
