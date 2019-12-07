package com.springBootWar.model;

import java.io.Serializable;

import com.springBootWar.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcedureResult implements Serializable {
	
	private static final long serialVersionUID = -8015413448573356237L;
	
	private Employee employee;
	
	private int row_count;

}