try:
    raise TabError("Bad hostname")
except Exception, e:
    print "error"
except IOError, ie:
    print ie.message
except TabError, te:
    print te.message
