@echo off
echo Please enter a year number suffix below...
echo.
set /p id=Enter Year Number: 

echo.

call AussieHolidaysApplication.bat %id%

echo.

pause

call OneDriveBackup.bat
pause
