package memorymanagement;

public class MemoryBlock {
	private long size;
	private Process process;
	
	private MemoryBlock() {	}
	
	public MemoryBlock(long size) {
		this();
		this.size = size;
	}
	
	public boolean load(Process process) {
		if (this.process == null) {
			this.process = process;
			return true;
		}
		return false;
	}
	
	public boolean unload(Process process) {
		if (this.process != null && this.process.equals(process)) {
			this.process = null;
			return true;
		}
		return false;
	}
	
	public Long getSize() {
		return size;
	}
	
	public Process getProcess() {
		return process;
	}
	
	public Long getUsedSpace() {
		return process.getSize();
	}
	
	public Long getFreeSpace() {
		return size - (process == null ? 0 : process.getSize());
	}
	
	public boolean canBeSubdivided() {
		return getFreeSpace() >= size/2;
	}
	
	public MemoryBlock getEmptyNewBlock() {
		return new MemoryBlock(size /= 2);
	}
	
	@Override
	public String toString() {
		return "{" + (process == null ? "null" : process.toString()) + ".." + size + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof MemoryBlock && size == ((MemoryBlock) obj).size && process == ((MemoryBlock) obj).process;
	}

	public void merge(MemoryBlock block) {
		size += block.getSize();
	}
}
