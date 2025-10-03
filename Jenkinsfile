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


//             // Email Notification
//                         emailext (
//                             subject: "Build ${currentBuild.fullDisplayName} - ${currentBuild.currentResult}",
//                             body: """<p>Build URL: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
//                                      <p>Status: ${currentBuild.currentResult}</p>
//                                      <p>Check test reports: <a href="${env.BUILD_URL}HTML_20Report/">Execution Report</a></p>""",
//                             recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
//                             to: "youremail@example.com"
//                         )

emailext (
    subject: "Build ${env.JOB_NAME} #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
    body: """
        <html>
        <body>
            <p><b>Build Status:</b> ${currentBuild.currentResult}</p>
            <p><b>Project:</b> ${env.JOB_NAME}</p>
            <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>
            <p><b>Build URL:</b> <a href="${env.BUILD_URL}">Click here to view build report</a></p>
        </body>
        </html>
    """,
    mimeType: 'text/html',
    to: "sdet2engineer@gmail.com",
    from: "sdet2engineer@gmail.com",
    replyTo: "sdet2engineer@gmail.com"
)

        }
    }
}
