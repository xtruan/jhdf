package com.jamesmudd.jhdf;

import static java.nio.ByteOrder.LITTLE_ENDIAN;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import com.jamesmudd.jhdf.exceptions.HdfException;

public class GroupSymbolTableNode {

	private static final byte[] NODE_SIGNATURE = "SNOD".getBytes();
	
	private final short version;
	private final short numberOfEntries;
	private final SymbolTableEntry[] symbolTableEntries;
	
	public GroupSymbolTableNode(RandomAccessFile file, long address, int sizeOfOffsets) {
			try {
				FileChannel fc = file.getChannel();
				
				// B Tree Node Header
				int headerSize = 8;
				ByteBuffer header = ByteBuffer.allocate(headerSize);

				fc.read(header, address);
				header.rewind();
				
				byte[] formatSignitureByte = new byte[4];
				header.get(formatSignitureByte, 0, formatSignitureByte.length);

				// Verify signature
				if (!Arrays.equals(NODE_SIGNATURE, formatSignitureByte)) {
					throw new HdfException("B tree node signature not matched");
				}
				
				// Version Number
				version = header.get();

				// Move past reserved space
				header.position(6);
				
				final byte[] twoBytes = new byte[2];

				// Data Segment Size
				header.get(twoBytes);
				numberOfEntries = ByteBuffer.wrap(twoBytes).order(LITTLE_ENDIAN).getShort();
				System.out.println("numberOfSymbols = " + numberOfEntries);
				
				final int symbolTableEntryBytes = sizeOfOffsets + sizeOfOffsets + 8 + 16;
							
				symbolTableEntries = new SymbolTableEntry[numberOfEntries];
				for (int i = 0; i < numberOfEntries; i++) {
					long offset = address + headerSize + i * symbolTableEntryBytes;
					symbolTableEntries[i] = new SymbolTableEntry(file, offset, sizeOfOffsets);
				}
			}
			catch (Exception e) {
				// TODO improve message
				throw new HdfException("Error reading Group symbol table node", e);
			}
	}

	public short getVersion() {
		return version;
	}

	public short getNumberOfEntries() {
		return numberOfEntries;
	}

	public SymbolTableEntry[] getSymbolTableEntries() {
		return symbolTableEntries;
	}
}