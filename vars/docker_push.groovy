def call(String credId, String imageName) {
    withCredentials([usernamePassword(
        credentialsId: credId,
        passwordVariable: 'DOCKER_HUB_PASS',
        usernameVariable: 'DOCKER_HUB_USER'
    )]) {
        // Log into Docker Hub securely using standard input
        sh 'echo "$DOCKER_HUB_PASS" | docker login -u "$DOCKER_HUB_USER" --password-stdin'
        
        // Tag the image (Ensure the syntax is valid Groovy interpolation)
        sh "docker image tag ${imageName} \$DOCKER_HUB_USER/${imageName}:latest"
        
        // Push the newly tagged image to the registry
        sh "docker push \$DOCKER_HUB_USER/${imageName}:latest"
    }
}
