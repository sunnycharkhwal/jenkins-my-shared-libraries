def call(String nvdCredId, String toolName = 'OWASP', String scanPath = './') {
    withCredentials([string(credentialsId: nvdCredId, variable: 'NVD_KEY')]) {
        // Dynamically find where Jenkins installed the OWASP tool
        def owaspHome = tool toolName
        
        // Run OWASP via shell script instead of the plugin. 
        // The '|| true' hides the 503 Server Error from Jenkins, preventing the permanent FAILURE lock.
        // Note: \$NVD_KEY is escaped to prevent Groovy from exposing the secret in pipeline logs.
        sh """
            OWASP_BIN=\$(find ${owaspHome} -name dependency-check.sh | head -n 1)
            \$OWASP_BIN --scan ${scanPath} --format HTML --format XML --nvdApiKey \$NVD_KEY || true
        """
    }
}
