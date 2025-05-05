#!/bin/bash

# Backend Spring Boot
echo "Insignite activation script"
echo "Starting Spring Boot on port 8080..."
/usr/bin/env /usr/lib/jvm/java-17-openjdk-amd64/bin/java @/tmp/cp_dlhil59xagkht4f1lnbqf1yn0.argfile com.insignite.InsigniteApplication > logs/spring.log 2>&1 & > /dev/null 2>&1 
SPRING_PID=$!


# Frontend React.js
echo "Starting React frontend on port 3000..."
cd frontend || exit
nohup npm install
nohup npm start & > ../logs/react.log 2>&1 & > /dev/null 2>&1 
REACT_PID=$!
cd ..

# Flask
echo "Starting Flask service on port 5000..."
cd python-service || exit
export FLASK_APP=app.py
export FLASK_RUN_PORT=5000
python3 app.py > ../logs/flask.log 2>&1 & > /dev/null 2>&1 
FLASK_PID=$!
cd ..


trap "kill $SPRING_PID $FLASK_PID $REACT_PID" EXIT
echo "Press Ctrl+C to stop services."

wait
