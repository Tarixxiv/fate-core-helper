# Fate Core Helper
## Description
A javafx destkop app for generating characters for tabletop rpgs in Fate Core system.
App pulls random lines from resource files to fill aspect fields. 
Skill are pulled from in-app customizable list and arranged in a pyramid according to Fate Core RPG system rules.
 

## How to use
 - Open Jar file
 - Click Generate button until you are satisfied with one of the aspect fields or pyramid column
   - If some of the skill points were left unused, the label below the pyramid will tell you the number
 - Lock columns and fields that satisfy you and continue pressing Generate
 - Once done click Output to clipboard and paste your output into desired place
 - You may also edit the skill list

### Loading character
 - Click load
 - Paste your character

### Format for pasting

<p>HC: Aspect</p>
<p>Tr: Aspect</p>
<p>* Aspect</p>
<p>* Aspect</p>
<p>* Aspect</p>
<br/>
<p>+4  |  skill</p>
<p>+3  |  skill |  skill</p>
<p>+2  |  skill |  skill</p>
<p>+1  |  skill |  skill</p>

^=only paste rows numbers which are filled, don't leave empty rows between filled ones.
The line break between aspects is required.
