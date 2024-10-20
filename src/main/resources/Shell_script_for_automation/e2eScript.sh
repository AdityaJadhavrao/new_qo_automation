#! /bin/bash

DOWNLOADS_DIR="$HOME/Downloads"  # Downloaded zip file path
TARGET_DIR="/home/excellarate/Documents/E2E_automation/html-office/crx/app"  # Target directory path
AUTOMATION_COMMAND="node monkey pageSets/all.json "  # Command to be inititated

# Recent zip file from downloads folder
ZIP_FILE=$(ls -t "$DOWNLOADS_DIR"/*.zip | head -n 1)

# Check if a zip file was found
if [[ -z "$ZIP_FILE" ]]; then
    echo "No zip file found in $DOWNLOAD_DIR"
    exit 1
fi

echo "Latest build found: $ZIP_FILE"

# Extract the base name of the zip file which is recently downloaded
ZIP_FILE_NAME=$(basename "$ZIP_FILE")

# Delete all content from the target directory
echo "Cleaning up target directory: $TARGET_DIR"
rm -rf "$TARGET_DIR"/*

# Extract the zip file into the target directory
unzip -o "$ZIP_FILE" -d "$TARGET_DIR"

echo "Extraction complete. Files moved to $TARGET_DIR"
echo "Downloaded zip file: $ZIP_FILE_NAME"
echo "Extracted zip file: $ZIP_FILE"

cd "/home/excellarate/Documents/E2E_automation/html-office/crx" || { echo "Failed to navigate to required directory"; exit 1; }

cd "/home/excellarate/Documents/E2E_automation/html-office/crx/e2eTests" || { echo "Failed to navigate to next folder"; exit 1; }

# Run the automation command in to the local terminal of Ubuntu and not in IDE
gnome-terminal -- bash -c "$AUTOMATION_COMMAND; exec bash"

# Run the automation command in to the local terminal of MAC and not in IDE
#osascript -e "$AUTOMATION_COMMAND"

# Check if the command was successful
if [ $? -eq 0 ]; then
    echo "Automation command executed successfully."
else
    echo "Automation command failed."
fi

