package com.springBootWar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import com.springBootWar.entity.Employee;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FunctionResult implements Serializable {
	
	private static final long serialVersionUID = -8015413448573356237L;

	private Employee employee;

	private int row_count;
}