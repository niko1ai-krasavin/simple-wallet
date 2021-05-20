pipeline {
    agent { dockerfile true }
    stages {
        stage('Deploy') {
             steps {
                sh 'docker run --rm --name simple-wallet -d -p 8081:8080 simple-wallet'
            }
        }
    }
}