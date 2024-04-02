@echo off

:: Pre App work before execution
call PreApp.bat

:: get user input
echo Please enter a year number suffix below...
echo.
set /p id=Enter Year Number: 

echo.

call AussieHolidaysApplication.bat %id%

echo.

:: Post App work after execution
call PostApp.bat

pause

call OneDriveBackup.bat
pause
