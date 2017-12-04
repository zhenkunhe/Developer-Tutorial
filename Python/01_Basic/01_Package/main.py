#!/usr/bin/python
# -*- coding: utf-8 -*-　
import package01.Account
from package01.module01 import SubDemo

if __name__ == "__main__":
    acct = package01.Account.Account('Justin', '123-4567', 1000)
    acct.deposit(500)
    acct.withdraw(200)
    print acct

    a = SubDemo(12, 34)
    a.hello()
    a.bye()
    b = SubDemo(56, 78)
    b.hello()
    b.bye()

