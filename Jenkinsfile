pipeline {
    agent { dockerfile true }
    stages {
        stage('Deploy') {
             steps {
                sh 'docker run --rm -d -p 8081:8080'
            }
        }
    }
}