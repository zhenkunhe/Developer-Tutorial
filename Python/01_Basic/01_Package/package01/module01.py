#!/usr/bin/python
# -*- coding: utf-8 -*-　
class Demo(object):
    __x = 0

    def __init__(self, i):
        self.__i = i
        Demo.__x += 1

    def __str__(self):
        return str(self.__i)

    def hello(self):
        print("hello " + self.__str__())

    @classmethod
    def getX(cls):
        return cls.__x


class Other(object):
    def __init__(self, k):
        self.k = k

    def __str__(self):
        return str(self.k)

    def hello(self):
        print("hello, world")

    def bye(self):
        print("Good-bye!", self.__str__())


class SubDemo(Demo, Other):
    def __init__(self, i, j):
        super(SubDemo, self).__init__(i)
        self.__j = j

    def __str__(self):
        return super(SubDemo, self).__str__() + "+" + str(self.__j)
