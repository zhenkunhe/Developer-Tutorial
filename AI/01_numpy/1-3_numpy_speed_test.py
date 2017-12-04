import numpy as np
import timeit

normal_py_sec = timeit.timeit('sum(x*x for x in xrange(1000))',number=10000)
navie_np_sec = timeit.timeit('sum(na*na)',setup="import numpy as np; na=np.arange(1000)",number=10000)
good_np_sec = timeit.timeit('na.dot(na)',setup="import numpy as np;na=np.arange(1000)",number=10000)

print ("Normal Python: %f sec" %normal_py_sec)
print ("Naive Numpy: %f sec" %navie_np_sec)
print ("Good Numpy: %f sec" %good_np_sec)
