pipeline {
    agent any

    tools {
        maven 'Maven'    // Global Tool Config se name
        jdk 'Java_JDK'       // Global Tool Config se name
    }

    triggers {
        pollSCM('H/5 * * * *')   // Har 5 min me GitHub repo check karega
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ojasvii/orangehrm-automation-framework.git'
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
                    reportDir: 'test-output/ExtentReports', // Extent report folder
                    reportFiles: 'index.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
