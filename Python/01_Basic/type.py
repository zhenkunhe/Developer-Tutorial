class OldStyle:
    pass


class NewStyle(object):
    pass


print type(OldStyle)  # prints: <type 'classobj'>

print type(NewStyle)  # prints <type 'type'>
