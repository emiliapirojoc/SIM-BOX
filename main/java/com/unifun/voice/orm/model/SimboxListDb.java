package com.unifun.voice.orm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
//@NamedQuery(name="SimboxListDb.getAll",query="select t from SimboxListDb t")
@Data
@Entity
@Table(name="SimboxList")
public class SimboxListDb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="text")
	private String name;

}
