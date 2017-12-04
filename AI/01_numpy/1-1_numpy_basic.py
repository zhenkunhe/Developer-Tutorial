import numpy as np
a = np.array([0,1,2,3,4,5])
print "a =",a.ndim
print "a.shape =",a.shape
print "--------------------------------"
b = a.reshape((3,2))
print "b =",b
print "b.ndim =",b.ndim
print "b.shape =",b.shape
print "--------------------------------"
b[1][0] = 77
print "b[1][0] = 77"
print "b =",b
print "a =",a
print "--------------------------------"
c = a.reshape((3,2)).copy()
print "c =",c
c[0][0] = -99
print "c[0][0] = -99"
print "a =",a
print "c =",c