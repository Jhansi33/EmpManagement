[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
$mavenUrl = "https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip"
$zipPath = "$env:TEMP\maven.zip"
$extractPath = "$env:USERPROFILE\maven"

Write-Host "Downloading Maven..."
Invoke-WebRequest -Uri $mavenUrl -OutFile $zipPath

Write-Host "Extracting Maven..."
if (Test-Path $extractPath) { Remove-Item -Recurse -Force $extractPath }
Expand-Archive -Path $zipPath -DestinationPath $extractPath -Force

$mavenHome = "$extractPath\apache-maven-3.9.9"
$mavenBin = "$mavenHome\bin"

Write-Host "Configuring Environment Variables..."
[Environment]::SetEnvironmentVariable("M2_HOME", $mavenHome, "User")
[Environment]::SetEnvironmentVariable("MAVEN_HOME", $mavenHome, "User")

$currentPath = [Environment]::GetEnvironmentVariable("Path", "User")
if ($currentPath -notmatch "apache-maven") {
    $newPath = "$currentPath;$mavenBin"
    [Environment]::SetEnvironmentVariable("Path", $newPath, "User")
}

Write-Host "Maven successfully downloaded to $mavenHome and added to User PATH."
