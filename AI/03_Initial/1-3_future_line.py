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

f1 = sp.poly1d(sp.polyfit(x, y, 1))
f2 = sp.poly1d(sp.polyfit(x, y, 2))
f3 = sp.poly1d(sp.polyfit(x, y, 3))
f10 = sp.poly1d(sp.polyfit(x, y, 10))
f100 = sp.poly1d(sp.polyfit(x, y, 100))

#print("Error inflection=%f" % (fa_error+fb_error))

mx=sp.linspace(0, 6 * 7 * 24, 100)
models = [f1, f2, f3, f10, f100]

for model, color in zip(models, colors):
    plt.plot(mx, model(mx),  linewidth=2, c=color)

plt.legend(["d=%i" % m.order for m in models], loc="upper left")
plt.autoscale(tight=True)
plt.ylim(ymin=0,ymax=10000)

plt.grid()
plt.show()