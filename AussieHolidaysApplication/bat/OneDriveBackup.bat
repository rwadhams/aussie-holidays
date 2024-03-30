@echo off
REM OneDriveBackup for AussieHolidays data files and reports

if not exist "%userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\AussieHolidays\" mkdir %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\AussieHolidays

xcopy *.xml %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\AussieHolidays /Y

xcopy out\*.* %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\AussieHolidays\out /I /Y
