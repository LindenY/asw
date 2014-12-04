package ca.uwaterloo.asw;

import ca.uwaterloo.asw.internal.InstructionNode;

public abstract class WorkerManager<T> {

	protected int coreNumWorkers;
	protected int maxNumWorkers;
	
	protected DataStore dataStore;
	protected InstructionResolver instructionResolver;
	
	public WorkerManager(int coreNumWorkers,
						 int maxNumWorkers,
						 DataStore dataNodeStore,
						 InstructionResolver instructionResolver) {
		
		this.coreNumWorkers = coreNumWorkers;
		this.maxNumWorkers = maxNumWorkers;
		
		this.dataStore = dataNodeStore;
		this.instructionResolver = instructionResolver;
	}

	public int getCoreNumWorkers() {
		return coreNumWorkers;
	}

	public void setCoreNumWorkers(int coreNumWorkers) {
		this.coreNumWorkers = coreNumWorkers;
	}

	public int getMaxNumWorkers() {
		return maxNumWorkers;
	}

	public void setMaxNumWorkers(int maxNumWorkers) {
		this.maxNumWorkers = maxNumWorkers;
	}

	public DataStore getDataNodeStore() {
		return dataStore;
	}

	public void setDataNodeStore(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	public InstructionResolver getInstructionResolver() {
		return instructionResolver;
	}

	public void setInstructionResolver(InstructionResolver instructionResolver) {
		this.instructionResolver = instructionResolver;
	}
	
	@SuppressWarnings("unchecked")
	public void addInstructionProduceDataToDataStore(
			Instruction<?, ?> instruction) {
		
		if (instruction.getResult() == null) {
			return;
		}
		String produceDataName = InstructionNode
				.getInstructionProduceDataName((Class<? extends Instruction<?, ?>>) instruction
						.getClass());
		dataStore.add(instruction.getResult(), produceDataName);
	}
	
	abstract public T start();
	abstract public void shutDown();
	abstract public void shutDownNow();
	abstract public void awaitShutDown(int timeOut) throws InterruptedException;
}
