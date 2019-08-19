#-------------------------------------------------------------------------------
# This file is part of jHDF. A pure Java library for accessing HDF5 files.
# 
# http://jhdf.io
# 
# Copyright 2019 James Mudd
# 
# MIT License see 'LICENSE' file
#-------------------------------------------------------------------------------
import h5py

import numpy as np

'''
The idea of this test is to write unusual dataset and check they still work
'''
def write_chunked_datasets(f):

    # 8D data, chunked compressed
    data = np.arange(20160).reshape(2, 3, 4, 5, 6, 7, 2, 2)
    f.create_dataset('8D_int16', data=data, dtype='i2', chunks=(2,3,1,2,3,1,1,2), compression="gzip")

    # Small data with inappropriate chunking
    data = np.arange(5*5*5).reshape(5, 5, 5);
    f.create_dataset('1D_int16', data=data, dtype='i2', chunks=(4, 4, 4), compression="gzip")

    f.flush()
    f.close()

if __name__ == '__main__':
    print('Making odd test files...')

    f = h5py.File('test_odd_datasets_earliest.hdf5', 'w', libver='earliest')
    write_chunked_datasets(f)
    print('test_compressed_chunked_datasets_earliest.hdf5')

# Start working just on 1.8 files first
#     f = h5py.File('test_chunked_datasets_latest.hdf5', 'w', libver='latest')
#     write_chunked_datasets(f)
#     print('created test_chunked_datasets_latest.hdf5')
    
