# !/usr/bin/env python
# -*- coding: utf-8 -*-
import scipy as sp
import numpy as np

#標準平方差公式
def error(f,x,y):
    return sp.sum( (f(x)-y) **2 )

data = sp.genfromtxt("web_traffic.tsv",delimiter="\t")

#前十筆資料
print (data[:10])
#資料矩陣
print (data.shape)

#第一維度的資料
x = data[:,0]
#第二維度的資料
y = data[:,1]
#NaN資料的數目
print sp.sum(sp.isnan(y))

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

inflection = 3.5*7*24
xa = x[:inflection]
ya = y[:inflection]
xb = x[inflection:]
yb = y[inflection:]

fa = sp.poly1d(sp.polyfit(xa, ya, 1))
fb = sp.poly1d(sp.polyfit(xb, yb, 1))

fa_error = error(fa,xa,ya)
fb_error = error(fb,xb,yb)

print("Error inflection=%f" % (fa_error+fb_error))

mx = sp.linspace(0, x[-1], 1000)
plt.plot(mx,fa(mx),"r",linewidth=4)
plt.plot(mx,fb(mx),"g",linewidth=4)
plt.legend(["d=%i" % fa.order,"d=%i" % fb.order],loc="upper left")
plt.autoscale(tight=True)
plt.ylim(ymin=0)

plt.grid()
plt.show()