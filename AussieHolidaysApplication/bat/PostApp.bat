@echo off
:: copy various outputs to appropriate directories

copy /Y out\*.* backup

copy /Y out\*-holidays-by-date-report.html mobile\holidays.html

copy /Y out\*-school-holiday-report.html mobile\school.html
