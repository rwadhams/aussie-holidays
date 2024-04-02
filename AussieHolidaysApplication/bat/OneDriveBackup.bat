@echo off
REM OneDriveBackup for AussieHolidays data files and reports

if not exist "%userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\AussieHolidays\" mkdir %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\AussieHolidays

:: data
xcopy *.xml %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\AussieHolidays /Y

:: reports
xcopy backup\*.* %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\AussieHolidays\backup /I /Y

xcopy mobile\*.* %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\AussieHolidays\mobile /I /Y
