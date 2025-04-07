@echo off
set SERVICE_NAME=lms-service-api
SET PORT=9092
FOR /F "tokens=5" %%A IN ('netstat -ano ^| findstr :%PORT%') DO (
    echo Service %SERVICE_NAME% is running on port %PORT%
    exit /b 0
)
echo Service %SERVICE_NAME% is stopped on port %PORT%
exit /b 1
