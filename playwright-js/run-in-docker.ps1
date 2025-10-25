param(
  [string]$imageName = 'playwright-js-runner',
  [string]$projectDir = (Get-Location).Path
)

Write-Host "Building Docker image $imageName..."
docker build -t $imageName .

Write-Host "Running tests in container..."
# Mount project dir and capture output
${mount} = "${projectDir}:/work"
docker run --rm -v ${mount} -w /work $imageName

Write-Host "Container run finished. Reports (if any) are in the project directory."
