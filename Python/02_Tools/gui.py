import gi
gi.require_version('Gtk', '3.0')
from gi.repository import Gtk

label = Gtk.Label("Cold reboot count:")
button = Gtk.Button(label="Click at your own risk")
count = 1
f = open('workfile', 'w')

class LabelWindow(Gtk.Window):

    def __init__(self):
        Gtk.Window.__init__(self, title="Label Example")
        hbox = Gtk.Box(spacing=10)
        hbox.set_homogeneous(False)

        vbox_left = Gtk.Box(orientation=Gtk.Orientation.VERTICAL, spacing=10)
        vbox_left.set_homogeneous(False)
        hbox.pack_start(vbox_left, True, True, 0)
        
        button.connect("clicked", self.on_button_clicked)
        vbox_left.pack_start(label, True, True, 0)
        vbox_left.pack_start(button, True, True, 0)
        self.add(hbox)

    def on_button_clicked(self, widget):
        global count
        count = count + 1
        label.set_text("Cold reboot count:"+str(count))

window = LabelWindow()        
window.connect("delete-event", Gtk.main_quit)
window.show_all()
Gtk.main()

