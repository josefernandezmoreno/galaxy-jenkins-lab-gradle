pipeline {
    agent any
        stages {
            stage('Build') {
                agent {
                    docker { image 'gradle:7.5.1-jdk11' }
                    }
                    steps {
                        sh 'gradle build'
                        archiveArtifacts artifacts: 'build/libs/labgradle-*-SNAPSHOT.jar', fingerprint: true
                    }
            }
            stage('Test') {
                agent {
                    docker { image 'gradle:7.5.1-jdk11' }
                    }
                    steps {
                        sh 'gradle test'
                        junit 'build/test-results/test/TEST-*.xml'
                    }
            }
            stage('SonarQube') {
                steps {
                    script{
                        def scannerHome = tool 'scanner-default'
                        withSonarQubeEnv('sonar-server') {
                            sh "${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectKey=labgradle01 \
                            -Dsonar.projectName=labgradle01 \
                            -Dsonar.sources=src/main/kotlin \
                            -Dsonar.java.binaries=build/classes \
                            -Dsonar.tests=src/test/kotlin"
                        }
                    }
                }
            }
            stage('Build Image') {
                steps {
                    copyArtifacts filter: 'build/libs/labgradle-*-SNAPSHOT.jar',
                                    fingerprintArtifacts: true,
                                    projectName: '${JOB_NAME}',
                                    flatten: true,
                                    selector: specific('${BUILD_NUMBER}'),
                                    target: 'build';
                    sh 'docker --version'
                    sh 'docker-compose --version'
                    sh 'docker-compose build'
                }
            }
        }
}
