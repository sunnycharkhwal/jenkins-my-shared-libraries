def call(String imageName, String buildContext = '.') {
    // Dynamically adding common problem directories to .dockerignore 
    // to prevent permission denied errors during the Docker build context upload.
    sh '''
        echo "mysql-data/" >> .dockerignore
        echo ".git/" >> .dockerignore
    '''
    
    // Build the Docker image using the provided name and context
    sh "docker build -t ${imageName}:latest ${buildContext}"
}
