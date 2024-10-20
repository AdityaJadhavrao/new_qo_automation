#!/bin/bash

DOWNLOADS_DIR="$HOME/Downloads"  # Downloaded zip file path
TARGET_DIR="/home/excellarate/Documents/Open_stability/html-office/crx/app"  # Specify your target directory path

# Recent zip file from downloads folder
ZIP_FILE=$(ls -t "$DOWNLOADS_DIR"/*.zip | head -n 1)

# Check if a zip file was found
if [[ -z "$ZIP_FILE" ]]; then
    echo "No zip file found in $DOWNLOADS_DIR"
    exit 1
fi

echo "Latest build found: $ZIP_FILE"

# Extract the base name of the zip file which is recently downloaded
ZIP_FILE_NAME=$(basename "$ZIP_FILE")

# Extract the build number from the zip filename
BUILD_NUMBER=$(basename "$ZIP_FILE" | grep -oP '(?<=master_)\d+\.\d+\.\d+\.\d+')

# Step 3: Create the final build string with "_open_stability"
FINAL_BUILD_STRING="${BUILD_NUMBER}_open_stability"

AUTOMATION_COMMAND="python3 run_misc_test.py --open_stability --build=$FINAL_BUILD_STRING"  # Command to be initiated

# Delete all content from the target directory
echo "Cleaning up target directory: $TARGET_DIR"
rm -rf "$TARGET_DIR"/*

# Extract the zip file into the target directory
unzip -o "$ZIP_FILE" -d "$TARGET_DIR"

# Navigating to the testFiles directory
cd "/home/excellarate/Documents/Open_stability/html-office/crx/e2eTests/miscTests/openStabilityTest" || exit 1

# Define variables
testFiles_zip="testFiles_backup_open_stability.zip"
testFiles_dir="testFiles"

# Check if the directory exists
if [ -d "$testFiles_dir" ]; then
    rm -rf "$testFiles_dir"
else
    echo "Directory $testFiles_dir does not exist. Proceeding with extraction..."
fi

# Set PROJECT_DIR correctly
PROJECT_DIR="/home/excellarate/Documents/Open_stability/html-office/crx/e2eTests/miscTests/openStabilityTest"

zip_path="$PROJECT_DIR/$testFiles_zip"
testFiles_path="$PROJECT_DIR/$testFiles_dir"

# Check if the zip file exists
if [ ! -f "$zip_path" ]; then
    echo "Error: Zip file '$zip_path' does not exist in '$PROJECT_DIR'."
    exit 1
fi

# Create the target directory
mkdir "$testFiles_path"

# Create a temporary directory
temp_dir=$(mktemp -d)

# Extract the zip file into the temporary directory
echo_msg "Extracting contents of '$zip_path' into temporary directory '$temp_dir'..."
unzip -o "$zip_path" -d "$temp_dir"

# Identify the extracted folder (assuming there's only one)
extracted_folder=$(find "$temp_dir" -mindepth 1 -maxdepth 1 -type d)

if [ -n "$extracted_folder" ]; then
    # Move into the extracted folder
    echo_msg "Navigating into extracted folder: '$extracted_folder'..."
    
    # Move files from the extracted folder to the testFiles directory
    echo_msg "Moving files from '$extracted_folder' to '$testFiles_path'..."
    mv "$extracted_folder"/* "$testFiles_path/"
    
    # Clean up the temporary directory
    rm -rf "$temp_dir"  # Remove the temp directory
else
    echo_msg "Error: No folder found inside '$zip_path' or multiple folders present."
    rm -rf "$temp_dir"  # Clean up if there's an error
    exit 1
fi

echo "Extraction completed successfully."
echo "Extraction complete. Files moved to $TARGET_DIR"
echo "Downloaded zip file: $ZIP_FILE_NAME"
echo "Extracted zip file: $ZIP_FILE"
echo "Build number is=$BUILD_NUMBER"
echo "Final build string is =$FINAL_BUILD_STRING"

cd "/home/excellarate/Documents/Open_stability/html-office/crx/e2eTests" || { echo "Failed to navigate to next folder"; exit 1; }

# Run the automation command in the local terminal of Ubuntu and not in IDE
gnome-terminal -- bash -c "$AUTOMATION_COMMAND; exec bash"

# Check if the command was successful
if [ $? -eq 0 ]; then
    echo "Automation command executed successfully."
else
    echo "Automation command failed."
fi

echo "Running automation command: $AUTOMATION_COMMAND"
# Run your automation command here
$AUTOMATION_COMMAND  