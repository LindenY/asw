package ca.uwaterloo.asw.testObjects;

import ca.uwaterloo.asw.DataNode;
import ca.uwaterloo.asw.Instruction;
import ca.uwaterloo.asw.ToolResolver;

public class TestInstruction1 extends Instruction<DataNode, Long> {

	public TestInstruction1(ToolResolver toolResolver) {
		super(toolResolver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void preExecution() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long execute(DataNode requiredData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postExecution() {
		// TODO Auto-generated method stub
		
	}

}