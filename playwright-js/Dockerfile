# Playwright Docker image for running tests without installing Node locally
# Uses the official Playwright image which already includes browsers and required deps
FROM mcr.microsoft.com/playwright:focal

# Create app directory
WORKDIR /work

# Copy package files and install deps
COPY package.json package-lock.json* ./
RUN if [ -f package-lock.json ]; then npm ci; else npm install; fi

# Copy rest of project
COPY . .

# Install browsers (image usually contains them, but ensure)
RUN npx playwright install --with-deps

# Default command: run all tests and produce reports
CMD ["npx", "playwright", "test", "--reporter=list,junit,html"]
