def call(Map config = [:]) {
    // Define defaults: 1 HOUR timeout, and abort the pipeline if the gate fails
    def timeoutTime = config.get('time', 1)
    def timeoutUnit = config.get('unit', 'HOURS')
    def abortStatus = config.get('abortPipeline', true)

    // Apply the timeout block dynamically
    timeout(time: timeoutTime, unit: timeoutUnit) {
        
        // Wait for the SonarQube server to reply with the analysis results
        def qg = waitForQualityGate abortPipeline: abortStatus
        
        // Optional: You can print the status to the logs for better visibility
        if (qg.status != 'OK') {
            error "Pipeline aborted due to quality gate failure: ${qg.status}"
        } else {
            echo "Quality Gate passed with status: ${qg.status}"
        }
    }
}
