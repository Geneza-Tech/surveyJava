#!/bin/bash

set -e  # Exit if any command fails

echo "🔄 Navigating to app directory..."
cd app/

echo "🧹 Removing old JAR..."
rm -f com_geneza_lms-3.0.0.jar

echo "🔙 Going back to project root..."
cd ..


echo "🛠️ Building project with Maven..."
mvn clean package

echo "📦 Copying new JAR to app directory..."
cp target/com_geneza_lms-3.0.0.jar app/

echo "🔙 Returning to root..."
cd app/

echo "🚀 Starting the application in background..."
nohup java -jar com_geneza_lms-3.0.0.jar > myapp.log 2>&1 &

echo "✅ Deployment complete."
