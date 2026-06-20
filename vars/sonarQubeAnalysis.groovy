def call(String projectKey, String sources = '.', String toolName = 'Sonar', String serverName = 'Sonar') {
    // Fetch the installation path of the SonarQube Scanner tool dynamically
    def scannerHome = tool toolName
    
    // Add the tool's /bin directory to the PATH so the shell can find 'sonar-scanner'
    withEnv(["PATH+SONAR=${scannerHome}/bin"]) {
        
        // Connect to the SonarQube server configured in Jenkins
        withSonarQubeEnv(serverName) {
            
            // Execute the scan using the provided project key and source directory
            sh "sonar-scanner -Dsonar.projectKey=${projectKey} -Dsonar.sources=${sources}"
        }
    }
}
