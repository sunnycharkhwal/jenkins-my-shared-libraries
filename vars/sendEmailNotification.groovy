def call(String emailTo) {
    // Retrieve the current status of the pipeline (SUCCESS, FAILURE, UNSTABLE, etc.)
    def buildStatus = currentBuild.currentResult ?: 'SUCCESS' // Defaults to SUCCESS if not fully set yet

    if (buildStatus == 'SUCCESS') {
        emailext(
            subject: "Build Successful: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: "Congratulations! The Jenkins build for ${env.JOB_NAME} #${env.BUILD_NUMBER} was successful.\n\nYou can view the build details here: ${env.BUILD_URL}",
            to: emailTo
        )
    } else if (buildStatus == 'FAILURE') {
        emailext(
            subject: "Jenkins Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: "Unfortunately, the Jenkins build for ${env.JOB_NAME} #${env.BUILD_NUMBER} has failed.\n\nPlease investigate the issue here: ${env.BUILD_URL}",
            to: emailTo
        )
    } else {
        // Catch-all for ABORTED or UNSTABLE builds
        emailext(
            subject: "Jenkins Build ${buildStatus}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: "The Jenkins build for ${env.JOB_NAME} #${env.BUILD_NUMBER} finished with status: ${buildStatus}.\n\nPlease check the logs here: ${env.BUILD_URL}",
            to: emailTo
        )
    }
}
