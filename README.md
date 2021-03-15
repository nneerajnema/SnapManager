# RAWDelete
A Java8 based project to delete irrelevant RAW files after a shoot when corresponding JPEG files have been deleted.

The code needs three variables to start with:  
**baseDir**: Base directory containing all event specific folders  
**eventFolder**: Directory inside <baseDir> of the specific event, e.g., Birthday Celebration. The directory houses all JPEG files imported from camera memory.  
**rawFolder**: Directory which houses all RAW files. It may be RAW / NRW / CR2/ etc.  

###Windows example
```bash
C:\
└───Pics
    └───Birthday 2021
        └───RAW
```
baseDir: C:/Pics  
eventFolder: Birthday 2021  
rawFolder: RAW
