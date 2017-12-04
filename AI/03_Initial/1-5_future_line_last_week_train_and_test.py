# !/usr/bin/env python
# -*- coding: utf-8 -*-
import scipy as sp
import numpy as np

# all examples will have three classes in this file
colors = ['g', 'k', 'b', 'm', 'r']

#標準平方差公式
def error(f,x,y):
    return sp.sum( (f(x)-y) **2 )

data = sp.genfromtxt("web_traffic.tsv",delimiter="\t")

#第一維度的資料
x = data[:,0]
#第二維度的資料
y = data[:,1]

#取出不是NaN的資料
x = x[~sp.isnan(y)]
y = y[~sp.isnan(y)]

#根據資料繪製基本圖形
import matplotlib.pyplot as plt
plt.scatter(x,y)
plt.title("Web traffic over the last month")
plt.xlabel("Time")
plt.ylabel("Hits/hour")
plt.xticks([w*7*24 for w in range(10)],['week %i'%w for w in range(10)])

inflection = 3.5 * 7 * 24
xb = x[inflection:]
yb = y[inflection:]

# separating training from testing data
frac = 0.3
split_idx = int(frac * len(xb))
print (len(xb))
shuffled = sp.random.permutation(list(range(len(xb))))
print (shuffled )

test = sorted(shuffled[:split_idx])
train = sorted(shuffled[split_idx:])
fbt1 = sp.poly1d(sp.polyfit(xb[train], yb[train], 1))
fbt2 = sp.poly1d(sp.polyfit(xb[train], yb[train], 2))
fbt3 = sp.poly1d(sp.polyfit(xb[train], yb[train], 3))
fbt10 = sp.poly1d(sp.polyfit(xb[train], yb[train], 10))
fbt100 = sp.poly1d(sp.polyfit(xb[train], yb[train], 100))

models = [fbt1, fbt2, fbt3, fbt10, fbt100]

print("Test errors for only the time after inflection point")
for f in models:
    print("Error d=%i: %f" % (f.order, error(f, xb[test], yb[test])))

mx=sp.linspace(0, 6 * 7 * 24, 100)

for model, color in zip(models, colors):
    plt.plot(mx, model(mx),  linewidth=2, c=color)

plt.legend(["d=%i" % m.order for m in models], loc="upper left")
plt.autoscale(tight=True)
plt.ylim(ymin=0,ymax=10000)

from scipy.optimize import fsolve
print(fbt2)
print(fbt2 - 100000)
reached_max = fsolve(fbt2 - 100000, x0=800) / (7 * 24)
print("100,000 hits/hour expected at week %f" % reached_max[0])

plt.grid()
plt.show()
