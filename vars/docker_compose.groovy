def call(String serviceName = '') {
    // Start with the base command
    def command = 'docker compose up -d --build'
    
    // If a specific service name (like 'flask-app') is provided, append it
    if (serviceName) {
        command = "${command} ${serviceName}"
    }
    
    // Execute the shell command
    sh command
}
