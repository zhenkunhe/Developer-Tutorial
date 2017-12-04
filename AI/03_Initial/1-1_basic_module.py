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
plt.autoscale(tight=True)

#取得誤差最小的一階方程式參數
fp1,residuals,rank,sv,rcond = sp.polyfit(x, y, 1,full=True)
print ("Model parameters: %s" % fp1)
#根據方程式參數,來建立方程式
#ex: f1(x) = 2.59619213 * x + 989.02487106
f1 = sp.poly1d(fp1)
#誤差總和
print (error(f1,x,y))

#取得誤差最小的二階方程式參數
f2p = sp.polyfit(x, y, 2)
#根據方程式參數,來建立方程式
f2 = sp.poly1d(f2p)
#誤差總和
print (error(f2,x,y))

#取得誤差最小的100階方程式參數
f100p = sp.polyfit(x, y, 100)
#根據方程式參數,來建立方程式
f100 = sp.poly1d(f100p)
#誤差總和
print (error(f100,x,y))

fx = sp.linspace(0,x[-1],1000)
plt.plot(fx,f1(fx),linewidth=4)
#plt.plot(fx,f2(fx),linewidth=4,linestyle='dotted')
plt.plot(fx,f2(fx),linewidth=4)
plt.plot(fx,f100(fx),linewidth=4)
plt.legend(["d=%i" % f1.order,"d=%i" % f2.order,"d=%i" % f100.order],loc="upper left")

plt.grid()
plt.show()