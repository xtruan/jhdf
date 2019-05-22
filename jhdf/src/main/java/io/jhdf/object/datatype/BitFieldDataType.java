package io.jhdf.object.datatype;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.BitSet;

public class BitFieldDataType extends DataType implements OrderedDataType {

    private final ByteOrder order;
    private final boolean lowPadding;
    private final boolean highPadding;
    private final short bitOffset;
    private final short bitPrecision;

    public BitFieldDataType(ByteBuffer bb) {
        super(bb);

        if (classBits.get(0)) {
            order = ByteOrder.BIG_ENDIAN;
        } else {
            order = ByteOrder.LITTLE_ENDIAN;
        }

        lowPadding = classBits.get(1);
        highPadding = classBits.get(2);

        // Properties
        bitOffset = bb.getShort();
        bitPrecision = bb.getShort();
    }

    @Override
    public Class<?> getJavaType() {
        return BitSet.class;
    }


    @Override
    public ByteOrder getByteOrder() {
        return null;
    }

    public ByteOrder getOrder() {
        return order;
    }

    public boolean isLowPadding() {
        return lowPadding;
    }

    public boolean isHighPadding() {
        return highPadding;
    }

    public short getBitOffset() {
        return bitOffset;
    }

    public short getBitPrecision() {
        return bitPrecision;
    }

    @Override
    public String toString() {
        return "BitFieldDataType [" +
                "order=" + order +
                ", lowPadding=" + lowPadding +
                ", highPadding=" + highPadding +
                ", bitOffset=" + bitOffset +
                ", bitPrecision=" + bitPrecision +
                ']';
    }
}
