package com.BitC.Controls;

import javax.swing.*;
import java.awt.*;
import java.io.*;

//定義檔單清單方塊與資料夾樹狀結構的圖示
public class FileIconResource {
	public static ImageIcon
		ICON_JPG = new ImageIcon(
			FileIconResource.class.getResource("icon/icon_jpg.gif")),
		ICON_GIF = new ImageIcon(
			FileIconResource.class.getResource("icon/icon_gif.gif")),
		ICON_ClosedFolder = new ImageIcon(
			FileIconResource.class.getResource("icon/ClosedFolder.jpg")),
		ICON_OpenedFolder = new ImageIcon(
			FileIconResource.class.getResource("icon/OpenFolder.jpg")),
		ICON_File = new ImageIcon(
			FileIconResource.class.getResource("icon/File.gif")),
		ICON_HD = new ImageIcon(
			FileIconResource.class.getResource("icon/hd.jpg"));
}