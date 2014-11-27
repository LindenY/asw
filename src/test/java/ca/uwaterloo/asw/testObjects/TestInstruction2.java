package ca.uwaterloo.asw.testObjects;

import java.util.Date;

import ca.uwaterloo.asw.DataNode;
import ca.uwaterloo.asw.Instruction;
import ca.uwaterloo.asw.ToolResolver;
import ca.uwaterloo.asw.meta.DependOn;
import ca.uwaterloo.asw.meta.ProduceData;
import ca.uwaterloo.asw.meta.RequireData;

@RequireData(names = { "data0", "data1", "data2" }, 
			types = { String.class, Long.class, Date.class })
@ProduceData(name="produced")
@DependOn(instructions = {TestInstruction0.class, TestInstruction1.class})
public class TestInstruction2 extends Instruction<DataNode, String> {

	public TestInstruction2(ToolResolver toolResolver) {
		super(toolResolver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void preExecution() {
		// TODO Auto-generated method stub

	}

	@Override
	public String execute(DataNode requiredData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postExecution() {
		// TODO Auto-generated method stub

	}

}