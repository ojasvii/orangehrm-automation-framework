pipeline {
    agent any

    tools {
        maven 'Maven'        // Must match Global Tool Config name
        jdk 'Java_JDK'       // Must match Global Tool Config name
    }

    triggers {
        pollSCM('H/5 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ojasvii/orangehrm-automation-framework.git',
                    credentialsId: 'github-cred'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('List Reports') {
            steps {
                bat 'dir /s target\\surefire-reports'
            }
        }

        stage('Publish Reports') {
            steps {
                junit 'target/surefire-reports/*.xml'

                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: '**/ExecutionReport_*.html',
                    reportName: "Execution Report"
                ])
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'reports/ExecutionReport_*.html', fingerprint: true
        }
    }
}
