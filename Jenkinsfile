pipeline {
    agent {
        docker { image 'gradle:7.5.1-jdk11' }
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew build'
                archiveArtifacts artifacts: 'build/libs/labgradle-*-SNAPSHOT.jar', fingerprint: true
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
                junit '**/build/test-results/test/TEST-*.xml'
            }
        }
        stage('SonarQube') {
            steps {
                def scannerHome = tool 'scanner-default'
                withSonarQubeEnv('sonar-server') {
                    sh "${scannerHome}/bin/sonar-scanner \
                    -Dsonar.projectKey=labgradle01 \
                    -Dsonar.projectName=labgradle01 \
                    -Dsonar.sources=src/main/kotlin \
                    -Dsonar.java.binaries=build/classes/ \
                    -Dsonar.tests=src/test/kotlin \
            }
        }
    }
}
