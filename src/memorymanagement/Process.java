package memorymanagement;

public class Process {
	private String process_id;
	private long size;
	
	private Process() {
		size = 0;
	}
	
	public Process(String process_id, long size) {
		this();
		this.size = size;
		this.process_id = process_id;
	}
	
	public String getProcessId() {
		return process_id;
	}
	
	public Long getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		return process_id+":"+size;
	}
}
