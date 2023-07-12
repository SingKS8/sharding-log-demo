package xyz.opcal.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "sharding_log")
public class ShardingLogEntity implements Serializable {

	private static final long serialVersionUID = -657880566485027205L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date createDate;
	
	private String log;
	
	private String level;
}
