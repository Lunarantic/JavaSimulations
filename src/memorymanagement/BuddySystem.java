package memorymanagement;

import java.util.ArrayList;

public class BuddySystem {
	
	private ArrayList<MemoryBlock> memory_blocks = new ArrayList<>();
	
	private BuddySystem() {}
	
	public BuddySystem(long memory_size) {
		this();
		memory_blocks.add(new MemoryBlock(memory_size));
		System.out.println("Initialization\n" + memory_blocks);
	}

	private void load(Process process) {
		long needSpace = (long) Math.pow(2, Math.ceil(Math.log10(process.getSize())/Math.log10(2)));
		
		if (memory_blocks.size() == 1) {
			while (true) {
				if (memory_blocks.get(0).getSize() > needSpace) {
					memory_blocks.add(0, (memory_blocks.get(0).getEmptyNewBlock()));
				} else {
					break;
				}
			}
		}
		MemoryBlock toBeLoadedInto = null;
		int index = 0;
		for (MemoryBlock mBlock : memory_blocks) {
			index++;
			if (mBlock.getFreeSpace() >= needSpace && mBlock.canBeSubdivided()) {
				if (mBlock.load(process)) {
					break;
				}
				toBeLoadedInto = mBlock.getEmptyNewBlock();
				toBeLoadedInto.load(process);
				break;
			}
		}
		if(toBeLoadedInto != null) {
			memory_blocks.add(index, toBeLoadedInto);
		}
	}
	
	private void unload(Process process) {
		for (MemoryBlock mBlock : memory_blocks) {
			if (mBlock.unload(process)){
				break;
			}
		}
		
		while(memory_blocks.size() > 1) {
			if (memory_blocks.get(0).equals(memory_blocks.get(1))) {
				memory_blocks.get(1).merge(memory_blocks.get(0));
				memory_blocks.remove(0);
			} else {
				break;
			}
		}
	}
	
	public void action(Process process, boolean loadInMemory) {
		if (loadInMemory) {
			load(process);
			System.out.println("Allocated memory for process " + process.getProcessId());
		} else {
			unload(process);
			System.out.println("Emptied memory of process " + process.getProcessId());
		}
		System.out.println(memory_blocks);
	}
}