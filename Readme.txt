==============================================
=== MichaelM's EU2 Event Generator v. 1.21 ===
==============================================
+--------------------------------------------+
|       Released 3/24/07                     |
|       Original release: 9/9/06             |
+--------------------------------------------+


=== System Requirements ===

Java 1.5 or later (tested on 1.6)


=== Instructions ===

Unzip anywhere, then double-click Event_Generator.jar.
If that doesn't work, try making a .bat file with the following:

--- cut here ---

@echo off
java -jar Event_Generator.jar

--- cut here ---

This is also useful for debugging if the program hangs.

**************************************************************************
** Before running the program, edit eg.conf with Notepad and change the **
** "user_name" entry to your name. Alternatively, choose                **
** Options->Set user name.                                              **
**************************************************************************

Note:
 If you are unzipping over a prior version, you should probably NOT overwrite
 Templates/template-list.txt or eg.conf. If you were using AGCEEP or
 Interregnum data files, you should probably leave Data/countryNames.txt and
 Data/countryTags.txt.


=== Usage ===

-I hope the interface is self-explanatory.

-When "Sequential dates" is checked, both the start date and the deathdate
 will increase by the specified number of years on each successive event.
 This works even for random events.

-File->Load will bring up a file selector dialog. When a valid file is
 chosen, a dialog with previews of all events in the file will show. Select
 one and click Load, and all relevant fields in the main window will be
 filled (including the "AI Only" check box. [NOTE: In v. 1.1 and later, there
 is no "AI Only" check box.]).

-Comments will be loaded intact from event files (I hope...). Comments are
 allowed in the trigger and the command windows. I haven't tested this
 feature fully, so there may be some weirdnesses (if that's a word...).

-When an event is loaded, it would probably be a good idea to change the ID
 before generating anything from it.

-Macros can be used in all text fields (although only ${tag} and ${provid} are
 allowed in triggers and actions). For usage, see Macros.txt.

-Event texts will be exported when generating events when "Export text" is
 selected. The generator extracts the event name, event description, and all
 action names, and puts them in a comment at the start of the generated events.
 This comment can then be removed and placed in text.csv (and uncommented).
 Currently, there is no support for reusing texts for multiple events.


=== To Be Added ===

-Editing one event in the middle of a file
-A list of all triggers and a list of all commands, like in Spoonist's Victoria
 Event Builder
-Any reasonable request


=== Bugs ===
Probably somewhat numerous. :)
Post any questions in the thread at
http://forum.paradoxplaza.com/forum/showthread.php?t=264451.


=== Credits ===
The original EUG file handling (found in lib/eugFile.jar) was based on Petter
'Chaingun' Hansson's code from the alpha version of Eu2Vic. I have completely
reworked most of it (based on JFlex), but eug.parser.CWordFile still retains
its name and some of the code.

The GenericObject/SpecificObject framework is taken from Kinniken's
VictoriaEditor (a collaboration with two other students). I have made some
improvements and also added SpecificObjects for EU2 (and some non-functional
ones for CK, which I don't own).

The actual parsing code (eug.parser.EUGScanner and most of
eug.parser.CWordFile) is my own, done with the help of JFlex
(http://www.jflex.de). (For a little demonstration of how hard tokenizing was
without JFlex, see eug.parser.Tokenizer) I have also added support for
retaining comments while loading.


This project is actually well over a year old. I was on the point of releasing
it last August (2005), but it was still fairly clumsy and feature-barren. I
hope it was worth the wait!

-- MichaelM
