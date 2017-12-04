import numpy as np

a = np.array([0,1,2,3,4,5])
b = np.array([1,"stringy"])
c = np.array([1,"stringy",set([1,2,3])])

print "a*2 =",a*2
print "[1,2,3,4,5]*2 =",[1,2,3,4,5]*2
print "a**2 =",a**2
#print [1,2,3,4,5]**2 will fail
print "---------------------------------"
a=a**2
print "a=a**2"
print "a[np.array([2,3,4])] =",a[np.array([2,3,4])]
print "a>4 =",a>4
print "a[a>4] =",a[a>4]
print "a.clip(0,4) =",a.clip(0,4)           #a[a>4] = 4
print "---------------------------------"
c = np.array([1,2,np.NaN,3,4])
print "c =",c
print "np.isnan(c) =",np.isnan(c)
print "c[~np.isnan(c)] =",c[~np.isnan(c)]
print "np.mean(c[~np.isnan(c)]) =",np.mean(c[~np.isnan(c)])
print "---------------------------------"
print "a.dtype =",a.dtype
print "b.dtype =",b.dtype
print "c.dtype =",c.dtype
