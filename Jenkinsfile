pipeline {
    agent any

    tools {
        maven 'Maven'        // Must match Global Tool Config name
        jdk 'Java_JDK'       // Must match Global Tool Config name
    }

    triggers {
        pollSCM('H/5 * * * *')   // Poll GitHub repo every 5 minutes
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
                sh 'mvn clean test'
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML([
                    reportDir: 'reports',              // matches ExtentManager path
                    reportFiles: 'ExtentReport.html',  // report file name
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'   // Publish TestNG results
        }
    }
}
