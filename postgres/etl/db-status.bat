@echo off
setlocal EnableDelayedExpansion

:: PostgreSQL configuration
set "BASE_DIR=D:\learning\executable\postgres"
set "PG_BIN=%BASE_DIR%\bin"
set "PGDATA=%BASE_DIR%\data"
set "PGLOGS=%BASE_DIR%\logs"
set "PG_CTL=%PG_BIN%\pg_ctl.exe"
set "PSQL=%PG_BIN%\psql.exe"

echo PostgreSQL Status Check
echo =====================

:: Check if executables exist
if not exist "%PG_CTL%" (
    echo ERROR: pg_ctl executable not found at: %PG_CTL%
    exit /b 1
)

if not exist "%PSQL%" (
    echo ERROR: psql executable not found at: %PSQL%
    exit /b 1
)

:: Check server status
echo Checking server status...
"%PG_CTL%" status -D "%PGDATA%" >nul 2>&1
if !errorlevel! equ 0 (
    echo Server Status: RUNNING
) else (
    echo Server Status: STOPPED
    exit /b 1
)

echo Status check completed successfully
exit /b 0
