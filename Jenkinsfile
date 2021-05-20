pipeline {
    agent { label "linux" }
    stages {
        stage("JUnit testing and building") {
             steps {
                sh """
                    docker build -t simple-wallet .
                """
            }
        }
        stage("Running") {
            steps {
                sh """
                    docker run --rm -d -p 8081:8080 simple-wallet
                """
            }
        }
    }
}