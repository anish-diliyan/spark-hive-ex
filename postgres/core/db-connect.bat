@echo off
setlocal EnableDelayedExpansion

:: PostgreSQL configuration
set "PSQL=D:\learning\executable\postgres\bin\psql.exe"
set "DB_NAME=hive_store"
set "DB_USER=postgres"

:: Check if psql exists
if not exist "%PSQL%" (
    echo ERROR: psql executable not found at: %PSQL%
    exit /b 1
)

:: Check if database exists
"%PSQL%" -U %DB_USER% -d postgres -t -c "SELECT 1 FROM pg_database WHERE datname='%DB_NAME%'" | findstr "1" >nul
if !errorlevel! neq 0 (
    echo Database %DB_NAME% does not exist. Creating now...
    "%PSQL%" -U %DB_USER% -d postgres -c "CREATE DATABASE %DB_NAME%;"
    if !errorlevel! neq 0 (
        echo ERROR: Failed to create database %DB_NAME%
        exit /b 1
    ) else (
        echo SUCCESS: Database %DB_NAME% created successfully
    )
) else (
    echo SUCCESS: Database %DB_NAME% already exists
)

echo Connecting to database %DB_NAME%...
"%PSQL%" -U %DB_USER% -d %DB_NAME%

if !errorlevel! neq 0 (
    echo ERROR: Failed to connect to database %DB_NAME%
    echo Please check if:
    echo 1. PostgreSQL service is running
    echo 2. Database %DB_NAME% exists
    echo 3. User %DB_USER% has proper permissions
    exit /b 1
) else (
    echo SUCCESS: Connected to database %DB_NAME%
    echo Connection details:
    echo 1. Database: %DB_NAME%
    echo 2. User: %DB_USER%
    echo 3. Server: localhost
)
