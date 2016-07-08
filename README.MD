AsciiPanel 2.0
==============

## Basic
AsciiPanel simulates a code page 437 ASCII terminal display. It supports all
256 characters of codepage 437, arbitrary foreground colors, arbitrary
background colors, and arbitrary terminal sizes.

The default terminal size is 80x24 characters and default colors matching the
Windows Command Prompt are provided. The deafault font is the CP437_10x10.

This should be useful to roguelike developers.

## 2.0 Features

My version includes a couple 'fonts' to choose from. 
- CP437_9x16 
- CP437_8x8 
- CP437_10x10 
- CP437_12x12
- CP437_16x16

 Additional Fonts from the [Dwarf Fortress Tileset](http://dwarffortresswiki.org/Tileset_repository)
- DRAKE_10x10
- QBICFEET_10x10 
- TALRYTH_15x15 


To choose a specific font, you have to call the AsciiPanel Constructor with 3 Parameters. 
Pass the columns, rows and the AsciiFont you want.


The API and code are strongly based on [Trystan's AsciiPanel for Java](https://github.com/trystan/AsciiPanel).