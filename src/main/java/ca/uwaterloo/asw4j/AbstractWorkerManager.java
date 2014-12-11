package ca.uwaterloo.asw4j;

import java.lang.reflect.Type;

import ca.uwaterloo.asw4j.reflection.TypeToken;

public abstract class AbstractWorkerManager<T> implements WorkerManager<T> {

	protected DataStore dataStore;
	protected AbstractInstructionResolver instructionResolver;

	public AbstractWorkerManager(DataStore dataStore,
			AbstractInstructionResolver instructionResolver) {
		this.dataStore = dataStore;
		this.instructionResolver = instructionResolver;
	}

	public DataStore getDataStore() {
		return dataStore;
	}

	public InstructionResolver getInstructionResolver() {
		return instructionResolver;
	}

	public void setDataStore(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	public void setInstructionResolver(
			AbstractInstructionResolver instructionResolver) {
		this.instructionResolver = instructionResolver;
	}

	public void registerBalancer(TypeToken<?> typeToken, Balancer<?> balancer) {
		dataStore.registerBalancer(typeToken, balancer);
	}

	public void registerCombiner(TypeToken<?> typeToken, Combiner<?> combiner) {
		dataStore.registerCombiner(typeToken, combiner);
	}

	public void registerInstructionClass(
			Class<? extends Instruction<?, ?>> instructionClass) {
		instructionResolver.registerInstructionClass(instructionClass);
	}

	public void registerInstructionClass(
			Class<? extends Instruction<?, ?>> instructionClass,
			String produceDataName) {
		instructionResolver.registerInstructionClass(instructionClass,
				produceDataName);
	}

	public void registerInstructionClass(
			Class<? extends Instruction<?, ?>> instructionClass,
			String[] requireDataNames, Type[] requireDataTypes) {
		instructionResolver.registerInstructionClass(instructionClass,
				requireDataNames, requireDataTypes);
	}

	public void registerInstructionClass(
			Class<? extends Instruction<?, ?>> instructionClass,
			String[] requireDataNames, Type[] requireDataTypes,
			String produceDataName) {
		instructionResolver.registerInstructionClass(instructionClass,
				requireDataNames, requireDataTypes, produceDataName);
	}

	public void registerInstructionClass(
			Class<? extends Instruction<?, ?>> instructionClass,
			String[] requireDataNames, Type[] requireDataTypes,
			String produceDataName,
			Class<? extends Instruction<?, ?>>[] dependencies,
			boolean supportSingleton, boolean supportAsync) {
		instructionResolver.registerInstructionClass(instructionClass,
				requireDataNames, requireDataTypes, produceDataName);
	}

	public void registerInstructionClass(
			Class<? extends Instruction<?, ?>> instructionClass,
			String[] requireDataNames, Type[] requireDataTypes,
			String produceDataName, boolean supportSingleton) {
		instructionResolver.registerInstructionClass(instructionClass,
				requireDataNames, requireDataTypes, produceDataName,
				supportSingleton);
	}

	public int numberOfRegisteredInstruction() {
		return instructionResolver.numberOfRegisteredInstruction();
	}

}
