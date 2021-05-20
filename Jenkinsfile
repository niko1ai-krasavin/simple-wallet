pipeline {
    agent {
        dockerfile true
        label 'simple-wallet'
    }
    stages {
        stage('Deploy') {
             steps {
                sh 'docker run -p 8081:8080 -t simple-wallet -d'
            }
        }
    }
}