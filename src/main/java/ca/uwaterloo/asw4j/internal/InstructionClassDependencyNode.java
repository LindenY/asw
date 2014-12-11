package ca.uwaterloo.asw4j.internal;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.uwaterloo.asw4j.Instruction;

public class InstructionClassDependencyNode extends InstructionClassNode {
	
	public static enum DEPENDENCY_STATE implements STATE {
		BlockedByDependency("BlockedByDependency");

		private 
		
		public DEPENDENCY_STATE(String stateName) {
			
		}
		
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	private Set<InstructionClassDependencyNode> incomings;
	private Set<InstructionClassDependencyNode> outgoings;

	private final Class<? extends Instruction<?, ?>>[] dependencies;
	private final boolean supportAsync;

	public InstructionClassDependencyNode(
			Class<? extends Instruction<?, ?>> instructionClass,
			String[] requireDataNames, Type[] requireDataTypes,
			String produceDataName, Type produceDataType,
			boolean supportSingleton, boolean enablePooling,
			Class<? extends Instruction<?, ?>>[] dependencies,
			boolean supportAsync) {

		super(instructionClass, requireDataNames, requireDataTypes,
				produceDataName, produceDataType, supportSingleton,
				enablePooling);

		this.dependencies = dependencies;
		this.supportAsync = supportAsync;

		incomings = new HashSet<InstructionClassDependencyNode>();
		outgoings = new HashSet<InstructionClassDependencyNode>();
	}

	public Set<InstructionClassDependencyNode> getIncomings() {
		return incomings;
	}

	public Set<InstructionClassDependencyNode> getOutgoings() {
		return outgoings;
	}

	public Class<? extends Instruction<?, ?>>[] getDependencies() {
		return dependencies;
	}

	public boolean isSupportAsync() {
		return supportAsync;
	}

	public void addIncoming(InstructionClassDependencyNode dependencyNode) {
		incomings.add(dependencyNode);
	}

	public void addOutgoing(InstructionClassDependencyNode dependencyNode) {
		outgoings.add(dependencyNode);
	}
	
	@Override
	public void clear() {
		incomings.clear();
		outgoings.clear();
		super.clear();
	}

	public static void resolveDependencies(
			List<? extends InstructionClassDependencyNode> dependencyNodes) {

		Map<Class<?>, InstructionClassDependencyNode> instructionsMap = 
				new HashMap<Class<?>, InstructionClassDependencyNode>();
		
		for (InstructionClassDependencyNode node : dependencyNodes) {
			node.clear();
			instructionsMap.put(node.getInstructionClass(), node);
		}
		
		for (InstructionClassDependencyNode node : dependencyNodes) {
			if (node.getDependencies() == null) {
				continue;
			}
			
			for (Class<?> clz : node.getDependencies()) {
				InstructionClassDependencyNode dependent = instructionsMap.get(clz);
				
				if (dependent != null) {
					node.addOutgoing(dependent);
					dependent.addIncoming(node);
				}
			}
		}
	}
}
