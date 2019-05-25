package io.jhdf.dataset;

import io.jhdf.HdfFile;
import io.jhdf.api.Attribute;
import io.jhdf.api.Dataset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.BitSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BitfieldDatasetTest {

	private static final String FILE_NAME = "../test_bitfield.hdf5";

	private static HdfFile hdfFile;

	@BeforeAll
	static void setup() {
		String testFileUrl = BitfieldDatasetTest.class.getResource(FILE_NAME).getFile();
		hdfFile = new HdfFile(new File(testFileUrl));
	}

	@Test
	void testGetBitFieldData() {

		Dataset dataset = hdfFile.getDatasetByPath("/axis0");

		// The bitfild is an attribute called 'transposed'
		Attribute bitfield = dataset.getAttribute("transposed");

		assertThat(bitfield.getJavaType(), is(BitSet.class));
		assertThat(bitfield.isScalar(), is(true));
		bitfield.getData();
	}
}

