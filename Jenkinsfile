pipeline {
    agent {
        docker { image 'gradle:7.5.1-jdk11' }
    }
    stages {
        stage('Build') {
            steps {
                sh 'gradle build'
            }
        }
    }
}
